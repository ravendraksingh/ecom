package com.rks.orderservice.controller;

import com.rks.orderservice.dto.request.OrderRequest;
import com.rks.orderservice.dto.request.UpdateOrderRequest;
import com.rks.orderservice.dto.response.OrderResponse;
import com.rks.orderservice.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.StringTokenizer;

@Validated
@CrossOrigin(origins = {"http://localhost:3080", "http://localhost:3090", "http://localhost:8080", "http://localhost:8090"})
@RestController
@RequestMapping("/api/v1")
public class UserOrderController {
    public static final Logger logger = LoggerFactory.getLogger(UserOrderController.class);
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
