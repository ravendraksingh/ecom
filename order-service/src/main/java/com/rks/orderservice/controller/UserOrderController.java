package com.rks.orderservice.controller;

import com.rks.orderservice.dto.response.OrderResponse;
import com.rks.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Validated
@CrossOrigin(origins = {"http://localhost:3080", "http://localhost:3090", "http://localhost:8080", "http://localhost:8090"})
@RestController
@RequestMapping("/api/v1")
public class UserOrderController {
    private OrderService orderService;

    @Autowired
    public UserOrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping(value = "/users/{email}/orders/{orderId}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public OrderResponse getOrderByEmailAndOrderId(
            @PathVariable(value = "email") @NotEmpty(message = "User email cannot be null") String email,
            @PathVariable(value = "orderId") @Min(value = 1, message = "Incorrect order id") Long orderId) {
        return orderService.getOrderByUserEmailAndOrderId(email, orderId);
    }

    @GetMapping("/users/{email}/orders")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderResponse> getAllOrdersByEmail(@PathVariable String email) {
        return orderService.getAllOrdersByEmail(email);
    }
}
