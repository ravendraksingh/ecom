package com.rks.orderservice.controller;

import com.rks.orderservice.dto.response.OrderResponse;
import com.rks.orderservice.service.impl.SecureOrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/secure/api/v1")
public class SecureOrderController {

    @Autowired
    private SecureOrderServiceImpl secureOrderService;

    @GetMapping(value = "/orders/{orderId}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<OrderResponse> getOrder(@PathVariable Long orderId, HttpServletRequest request) throws Exception {
        return secureOrderService.processGetOrderById(orderId, request);
    }
}
