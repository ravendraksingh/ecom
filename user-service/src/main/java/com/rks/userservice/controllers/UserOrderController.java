package com.rks.userservice.controllers;

import com.rks.userservice.proxy.orderservice.OrderServiceProxy;
import com.rks.userservice.proxy.orderservice.dto.OrderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserOrderController {

    @Autowired
    private OrderServiceProxy orderServiceProxy;

    @GetMapping("/{email}/orders")
    public List<OrderResponse> getAllOrdersForUserByEmail(String email) {
        List<OrderResponse> orderResponseList = orderServiceProxy.getAllOrdersByEmail(email);
        System.out.println(orderResponseList);
        return orderResponseList;
    }
}
