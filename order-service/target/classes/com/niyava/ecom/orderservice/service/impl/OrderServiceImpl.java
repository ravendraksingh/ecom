package com.niyava.ecom.orderservice.service.impl;

import com.niyava.ecom.common.exceptions.NotFoundException;
import com.niyava.ecom.orderservice.caching.EmailOrderIdKeyGenerator;
import com.niyava.ecom.orderservice.dto.request.OrderRequest;
import com.niyava.ecom.orderservice.dto.request.UpdateOrderRequest;
import com.niyava.ecom.orderservice.dto.response.OrderResponse;
import com.niyava.ecom.orderservice.entity.Order;
import com.niyava.ecom.orderservice.exceptions.ServiceErrorFactory;
import com.niyava.ecom.orderservice.mappers.OrderMapper;
import com.niyava.ecom.orderservice.rabbitmq.OrderMessage;
import com.niyava.ecom.orderservice.repository.OrderRepository;
import com.niyava.ecom.orderservice.service.OrderService;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.niyava.ecom.orderservice.constants.OrderServiceErrorCodes.ORDER_NOT_FOUND;

//import io.micrometer.core.instrument.Counter;
//import io.micrometer.core.instrument.Metrics;

@Service
public class OrderServiceImpl implements OrderService {
    private static Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Value("${rabbitmq.enabled:false}")
    private boolean queueEnabled;
//    private OrderCreatedMessageProducer orderCreatedMessageProducer;
    private final OrderRepository orderRepository;

    private final OrderMapper orderMapper;
    @Value("${caching.enabled:false}")
    private boolean cachingEnabled;

    @Autowired
    private EmailOrderIdKeyGenerator emailOrderIdKeyGenerator;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

//    @Autowired(required = false)
//    public void setMessageProducer(@Nullable OrderCreatedMessageProducer orderCreatedMessageProducer) {
//        this.orderCreatedMessageProducer = orderCreatedMessageProducer;
//    }

//    public OrderServiceImpl(OrderRepository orderRepository, OrderCreatedMessageProducer orderCreatedMessageProducer, OrderMapper orderMapper) {
//        this.orderRepository = orderRepository;
//        this.orderCreatedMessageProducer = orderCreatedMessageProducer;
//        this.orderMapper = orderMapper;
//    }

    @Timed(value = "createorder.time", description = "Time taken to create order")
    @Transactional
    public OrderResponse createNewOrder(OrderRequest orderRequest) {
        log.info("Creating new order");
        if (log.isDebugEnabled()) {
            log.debug("orderRequest={}", orderRequest);
        }

        Order savedOrder = orderRepository.save(createOrderForOrderRequest(orderRequest));
        if (log.isDebugEnabled()) {
            log.debug("Order created successfully with orderId={}. savedOrder={}", savedOrder.getId(), savedOrder);
        }
        if (queueEnabled) {
            log.info("Queue is enabled");
            OrderMessage message = new OrderMessage(savedOrder.getId(),
                    savedOrder.getOrderDate(), savedOrder.getUserEmail(),
                    savedOrder.getOrderStatus(), savedOrder.getPaymentStatus(), savedOrder.getNetAmount());
            if (log.isDebugEnabled()) {
                log.debug("Creating orderMessage. orderMessage={}", message);
            }
            log.info("Sending orderMessage to message queue");
            //orderCreatedMessageProducer.sendMessage(message);
            log.info("Message successfully sent to queue");
        }
        return createOrderResponseForOrder(savedOrder);
    }

    @Transactional
    public OrderResponse getOrderById(Long orderId) {
        log.info("Fetching order details for order id={}", orderId);
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (!optionalOrder.isPresent()) {
            log.error("Order not found for order id={}", orderId);
            throw ServiceErrorFactory.getNamedException(ORDER_NOT_FOUND);
        }
        return createOrderResponseForOrder(optionalOrder.get());
    }

    public List<OrderResponse> findAllOrders() {
        log.info("Fetching all orders");
        List<Order> orders = new ArrayList<>();
        orders = (List<Order>) orderRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        if (orders.isEmpty()) {
//            throw ServiceErrorFactory.getNamedException(ORDER_NOT_FOUND);
            throw new NotFoundException("No orders found");
        }
        List<OrderResponse> response = orders.stream().map(this::createOrderResponseForOrder).collect(Collectors.toList());
        log.info("OrderResponse List: {}",  response);
        return response;
    }

    @Cacheable(value = "order-cache")
    @Timed(value = "getallorders.time", description = "Time taken to get all orders for email", percentiles = {0.5, 0.90})
    public List<OrderResponse> getAllOrdersByEmail(String email) {
        log.info("Fetching all orders for email={}", email);

        List<Order> orders = orderRepository.findAllByUserEmailOrderByIdDesc(email).stream().toList();
        if (orders.isEmpty()) {
            log.error("Order not found for email={}", email);
            increaseCount("/users/{email}/orders", "404");
            throw ServiceErrorFactory.getNamedException(ORDER_NOT_FOUND);
        }
        List<OrderResponse> orderResponseList = orders.stream().map(this::createOrderResponseForOrder).collect(Collectors.toList());
        increaseCount("/users/{email}/orders", "200");
        log.info("Successfully fetched orders for email={}", email);
        return orderResponseList;
    }

    @Cacheable(cacheNames = "order-cache", keyGenerator = "emailOrderIdKeyGenerator")
    public OrderResponse getOrderByUserEmailAndOrderId(String email, Long id) {
        log.info("fetching order for email={} and orderId={}", email, id);
        Order order = orderRepository.findOrderByUserEmailAndId(email, id);
        if (order == null) {
//            throw ServiceErrorFactory.getNamedException(ORDER_NOT_FOUND);
            throw new NotFoundException("Order not found for email="+ email + " and orderId=" + id);
        }
        OrderResponse orderResponse = createOrderResponseForOrder(order);
//        increaseCount("/users/{email}/orders/{orderId}", "200");
        return orderResponse;
    }

    @Override
    public List<OrderResponse> getAllOrdersByUserEmailAndOrderDate(String email, LocalDate orderDate) {
        log.info("Fetching order for email {} and orderDate {}", email, orderDate);
        List<Order> orders = orderRepository.findAllOrdersByUserEmailAndOrderDate(email, orderDate);
        if (orders.isEmpty()) {
            log.error("Order not found for email={} and orderDate={}", email, orderDate);
            throw ServiceErrorFactory.getNamedException(ORDER_NOT_FOUND);
        }
        return orders.stream().map(this::createOrderResponseForOrder).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void updateOrderStatus(Long orderId, String orderStatus) {
        log.info("Updating order status for orderId={}", orderId);
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (!optionalOrder.isPresent()) {
            log.error("Order not found for order id={}", orderId);
            throw ServiceErrorFactory.getNamedException(ORDER_NOT_FOUND);
        }
        Order fetchedOrder = optionalOrder.get();
        fetchedOrder.setOrderStatus(orderStatus);

        Order savedOrder = orderRepository.save(fetchedOrder);
        createOrderResponseForOrder(savedOrder);
    }

    @Override
    public List<Order> findByOrderStatus(String status) {
        log.info("Fetching all orders with order status={}", status);
        String orderStatus = status.toUpperCase();
        List<Order> orderList = orderRepository.findByOrderStatus(orderStatus);
        return orderList;
    }

    public void deleteAllOrders() {
        try {
            log.debug("Deleting all orders");
            orderRepository.deleteAll();
        } catch (Exception e) {
            log.error("Exception occurred. Message={}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Timed(value = "updateorder.time", description = "Time taken to update order")
    @Transactional
    public OrderResponse updateOrder(Long orderId, UpdateOrderRequest request) {
        log.info("Updating order with orderId={}", orderId);
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (!optionalOrder.isPresent()) {
            log.error("Order not found for order id={}", orderId);
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

    public List<OrderResponse> getOrdersPaginated(@RequestParam String email, Pageable pageRequest) {
        log.info("Fetching paginated order for page request: {}", pageRequest);
        Page<Order> orders ;
        orders = orderRepository.findAllByUserEmail(email, pageRequest);
        log.info(String.valueOf(orders));
        if (orders.isEmpty()) {
            throw ServiceErrorFactory.getNamedException(ORDER_NOT_FOUND);
        }
        List<OrderResponse> response = orders.stream().map(this::createOrderResponseForOrder).collect(Collectors.toList());
        log.info("OrderResponse List: {}", response);
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
