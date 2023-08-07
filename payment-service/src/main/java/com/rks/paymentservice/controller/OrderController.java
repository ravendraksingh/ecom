package com.rks.paymentservice.controller;

import com.rks.paymentservice.dto.request.OrderRequest;
import com.rks.paymentservice.dto.response.OrderResponse;

import com.rks.paymentservice.service.PaymentOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/v1/payments")
public class OrderController {

    @Autowired
    PaymentOrderService paymentOrderService;

    @PostMapping(value="/orders", produces="application/json", consumes="application/json")
    public OrderResponse create(@RequestBody OrderRequest request) {
        return paymentOrderService.create(request);
    }

    @GetMapping(value = "/orders/{orderId}", produces = "application/json")
    public OrderResponse getOrderByOrderId(@PathVariable(value = "orderId") Long orderId) {
        return paymentOrderService.get(orderId);
    }
}
