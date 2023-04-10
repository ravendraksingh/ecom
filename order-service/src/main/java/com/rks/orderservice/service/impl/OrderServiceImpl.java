package com.rks.orderservice.service.impl;

import com.rks.orderservice.dto.request.OrderRequest;
import com.rks.orderservice.dto.request.UpdateOrderRequest;
import com.rks.orderservice.dto.response.OrderResponse;
import com.rks.orderservice.entity.Order;
import com.rks.orderservice.exceptions.ServiceErrorFactory;
import com.rks.orderservice.mappers.OrderMapper;
import com.rks.orderservice.rabbitmq.OrderCreatedMessageProducer;
import com.rks.orderservice.rabbitmq.OrderMessage;
import com.rks.orderservice.repository.OrderRepository;
import com.rks.orderservice.service.OrderService;
//import io.micrometer.core.instrument.Counter;
//import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.rks.orderservice.constants.OrderServiceErrorCodes.ORDER_NOT_FOUND;

@Service
public class OrderServiceImpl implements OrderService {
    private static Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Value("${rabbitmq.enabled}")
    private boolean queueEnabled;
    private final OrderRepository orderRepository;
    private OrderCreatedMessageProducer orderCreatedMessageProducer;
    private final OrderMapper orderMapper;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

//    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, OrderCreatedMessageProducer orderCreatedMessageProducer, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderCreatedMessageProducer = orderCreatedMessageProducer;
        this.orderMapper = orderMapper;
    }

    @Timed(value = "createorder.time", description = "Time taken to create order")
    @Transactional
    public OrderResponse createNewOrder(OrderRequest orderRequest) {
        logger.info("Creating new order");
        if (logger.isDebugEnabled()) {
            logger.debug("orderRequest={}", orderRequest);
        }

        Order savedOrder = orderRepository.save(createOrderForOrderRequest(orderRequest));
        if (logger.isDebugEnabled()) {
            logger.debug("Order created successfully with orderId={}. savedOrder={}", savedOrder.getId(), savedOrder);
        }
        if (queueEnabled) {
            logger.info("Queue is enabled");
            OrderMessage message = new OrderMessage(savedOrder.getId(),
                    savedOrder.getOrderDate(), savedOrder.getUserEmail(),
                    savedOrder.getOrderStatus(), savedOrder.getPaymentStatus(), savedOrder.getNetAmount());
            if (logger.isDebugEnabled()) {
                logger.debug("Creating orderMessage. orderMessage={}", message);
            }
            logger.info("Sending orderMessage to message queue");
            orderCreatedMessageProducer.sendMessage(message);
            logger.info("Message successfully sent to queue");
        }
        return createOrderResponseForOrder(savedOrder);
    }

    @Transactional
    public OrderResponse getOrderById(Long orderId) {
        logger.info("Fetching order details for order id={}", orderId);
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (!optionalOrder.isPresent()) {
            logger.error("Order not found for order id={}", orderId);
            throw ServiceErrorFactory.getNamedException(ORDER_NOT_FOUND);
        }
        return createOrderResponseForOrder(optionalOrder.get());
    }

    public List<OrderResponse> findAllOrders() {
        logger.info("Fetching all orders");
        List<Order> orders = new ArrayList<>();
        //orderRepository.findAll().forEach(orders::add);
        orderRepository.findAll(Sort.by(Sort.Direction.DESC, "id")).forEach(orders::add);
        if (orders.isEmpty()) {
            throw ServiceErrorFactory.getNamedException(ORDER_NOT_FOUND);
        }
        List<OrderResponse> response = orders.stream().map(this::createOrderResponseForOrder).collect(Collectors.toList());
        logger.info("OrderResponse List: ");
        logger.info("" + response);
        return response;
    }

    @Timed(value = "getallorders.time", description = "Time taken to get all orders for email", percentiles = {0.5, 0.90})
    public List<OrderResponse> getAllOrdersByEmail(String email) {
        logger.info("Fetching all orders for email={}", email);

        List<Order> orders = orderRepository.findAllByUserEmailOrderByIdDesc(email).stream().collect(Collectors.toList());
        if (orders.isEmpty()) {
            logger.error("Order not found for email={}", email);
            increaseCount("/users/{email}/orders", "404");
            throw ServiceErrorFactory.getNamedException(ORDER_NOT_FOUND);
        }
        List<OrderResponse> orderResponseList = orders.stream().map(this::createOrderResponseForOrder).collect(Collectors.toList());
        increaseCount("/users/{email}/orders", "200");
        return orderResponseList;
    }

    public OrderResponse getOrderByUserEmailAndOrderId(String email, Long id) {
        logger.info("Fetching order for email={} and orderId={}", email, id);
        Order order = orderRepository.findOrderByUserEmailAndId(email, id);
        if (order == null) {
            logger.error("Order not found for email={} and orderId={}", email, id);
            throw ServiceErrorFactory.getNamedException(ORDER_NOT_FOUND);
        }
        OrderResponse orderResponse = createOrderResponseForOrder(order);
//        increaseCount("/users/{email}/orders/{orderId}", "200");
        return orderResponse;
    }

    @Override
    public List<OrderResponse> getAllOrdersByUserEmailAndOrderDate(String email, Date orderDate) {
        logger.info("Fetching order for email {} and orderDate {}", email, orderDate);
        List<Order> orders = orderRepository.findAllOrdersByUserEmailAndOrderDate(email, orderDate);
        if (orders.isEmpty()) {
            logger.error("Order not found for email={} and orderDate={}", email, orderDate);
            throw ServiceErrorFactory.getNamedException(ORDER_NOT_FOUND);
        }
        return orders.stream().map(this::createOrderResponseForOrder).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void updateOrderStatus(Long orderId, String orderStatus) {
        logger.info("Updating order status for orderId={}", orderId);
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (!optionalOrder.isPresent()) {
            logger.error("Order not found for order id={}", orderId);
            throw ServiceErrorFactory.getNamedException(ORDER_NOT_FOUND);
        }
        Order fetchedOrder = optionalOrder.get();
        fetchedOrder.setOrderStatus(orderStatus);

        Order savedOrder = orderRepository.save(fetchedOrder);
        createOrderResponseForOrder(savedOrder);
    }

    @Override
    public List<Order> findByOrderStatus(String status) {
        logger.info("Fetching all orders with order status={}", status);
        String orderStatus = status.toUpperCase();
        List<Order> orderList = orderRepository.findByOrderStatus(orderStatus);
        return orderList;
    }

    public void deleteAllOrders() {
        try {
            logger.debug("Deleting all orders");
            orderRepository.deleteAll();
        } catch (Exception e) {
            logger.error("Exception occurred. Message={}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Timed(value = "updateorder.time", description = "Time taken to update order")
    @Transactional
    public OrderResponse updateOrder(Long orderId, UpdateOrderRequest request) {
        logger.info("Updating order with orderId={}" + orderId);
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (!optionalOrder.isPresent()) {
            logger.error("Order not found for order id={}", orderId);
            throw ServiceErrorFactory.getNamedException(ORDER_NOT_FOUND);
        }
        Order order = optionalOrder.get();
        if (request.getOrderStatus() != null) {
            order.setOrderStatus(request.getOrderStatus());
        }
        if (request.getPaymentStatus() != null) {
            order.setPaymentStatus(request.getPaymentStatus());
        }
        Order savedOrder = orderRepository.save(order);
        OrderResponse response = new OrderResponse();
        orderMapper.map(savedOrder, response);
        return response;
    }

    private OrderResponse createOrderResponseForOrder(Order order) {
        OrderResponse orderResponse = new OrderResponse();
        orderMapper.map(order, orderResponse);
        return orderResponse;
    }

    private Order createOrderForOrderRequest(OrderRequest orderRequest) {
        Order order = new Order();
        orderMapper.map(orderRequest, order);
        return order;
    }

    private void increaseCount(String apiName, String httpResponseCode) {
        Counter counter = Metrics.counter("api-stats", "api-name", apiName, "http-response", httpResponseCode);
        counter.increment();
    }
}
