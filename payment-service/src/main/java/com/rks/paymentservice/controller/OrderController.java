package com.rks.paymentservice.controller;

import com.rks.paymentservice.dto.request.OrderRequest;
import com.rks.paymentservice.dto.request.OrderResponse;

import com.rks.paymentservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/v1")
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping(value="/orders", produces="application/json", consumes="application/json")
    public OrderResponse create(@RequestBody OrderRequest request) {
        return orderService.create(request);
    }

    @GetMapping(value = "/orders/{orderId}", produces = "application/json")
    public OrderResponse getOrderByOrderId(@PathVariable(value = "orderId") Long orderId) {
        return orderService.get(orderId);
    }
}
