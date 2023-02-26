package com.rks.paymentservice.service;

import com.rks.paymentservice.controller.OrderController;
import com.rks.paymentservice.dto.request.OrderResponse;
import com.rks.paymentservice.entity.Order;
import com.rks.paymentservice.dto.request.OrderRequest;
import com.rks.paymentservice.repository.OrderRepository;
import com.rks.paymentservice.util.DateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.hateoas.Link;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.TimeZone;

import static com.rks.paymentservice.constants.Constant.ORDER_STATUS_CREATED;
//import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
//import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Slf4j
@Service
public class PaymentOrderService {

    @Autowired
    private OrderRepository orderRepository;

    public OrderResponse create(OrderRequest request) {
        log.info("Creating a new order for, request=" + request);
        Order po = createOrderForOrderRequest(request);
        Order savedOrder = orderRepository.save(po);
        OrderResponse response = createOrderResponseForOrder(savedOrder);
        log.info("response = " + response);
        return response;
    }

    public OrderResponse get(Long orderId) {
        Optional<Order> optionalPO = orderRepository.findById(orderId);
        if (!optionalPO.isPresent()) {
            throw new RuntimeException("Order id " + orderId + " not found");
        }
        return createOrderResponseForOrder(optionalPO.get());
    }
    /**
     *      "customer": {
     *        "first_name": "John",
     *        "last_name": "Doe",
     *        "mobile": "9800000000",
     *        "mobile_alt": "9800000000",
     *        "email": "john.doe@somedomain.com",
     *        "email_alt": "john.doe@somedomain.com"
     *      }
     *
     *      "device": {
     *          "init_channel": "internet",
     *          "ip": "124.124.1.1",
     *          "mac": "11-AC-58-21-1B-AA",
     *          "imei": "990000112233445",
     *          "user_agent": "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:51.0) Gecko/20100101 Firefox/51.0",
     *          "accept_header": "text/html",
     *         "fingerprintid": "61b12c18b5d0cf901be34a23ca64bb19"
     *       }
     *
     *        "additional_info":{
     *        "additional_info1": "Details1",
     *        "additional_info2": "Details2",
     *         .
     *         .
     *         .
     *        "additional_info10": "Details10"
     *       }
     */
    private Order createOrderForOrderRequest(OrderRequest request) {
        Order order = new Order();
        order.setOrderid(request.getOrderid());
        order.setEntity(request.getEntity());
        order.setMercid(request.getMercid());
        order.setOrder_date(request.getOrder_date());
        order.setAmount(request.getAmount());
        order.setAmount_due(request.getAmount_due());
        order.setAmount_paid(request.getAmount_paid());
        order.setCurrency(request.getCurrency());
        order.setRu(request.getRu());
        order.setItemcode(request.getItemcode());
        order.setSettlement_lob(request.getSettlement_lob());
        order.setCustomer(request.getCustomer());
        order.setDevice(request.getDevice());
        order.setNext_step("redirect");
        order.setStatus(ORDER_STATUS_CREATED);
        order.setCreated_at(new Timestamp(System.currentTimeMillis()));
        return order;
    }

    private OrderResponse createOrderResponseForOrder(Order order) {
        log.info("in createOrderResponseForOrder");
        OrderResponse response = new OrderResponse();
        response.setEntity(order.getEntity());
        response.setPgorderid(order.getPgorderid());
        response.setOrderid(order.getOrderid());
        response.setMercid(order.getMercid());
        //response.setOrder_date(order.getOrder_date());
        response.setOrder_date(DateTimeUtil.getISODateWithTimezone(order.getOrder_date()));
        response.setAmount(order.getAmount());
        response.setAmount_due(order.getAmount_due());
        response.setAmount_paid(order.getAmount_paid());
        response.setCurrency(order.getCurrency());
        response.setRu(order.getRu());
        response.setItemcode(order.getItemcode());
        response.setSettlement_lob(order.getSettlement_lob());
        response.setCustomer(order.getCustomer());
        response.setDevice(order.getDevice());
        response.setPayment_categories(order.getPayment_categories());
        response.setNext_step(order.getNext_step());
        response.setStatus(order.getStatus());
        response.setCreated_at(DateTimeUtil.getISODateWithTimezone(order.getCreated_at()));
//        Link selfLink = linkTo(methodOn(OrderController.class).getOrderByOrderId(order.getPgorderid())).withSelfRel();
//        response.add(selfLink);
        return response;
    }
}
