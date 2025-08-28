package com.niyava.ecom.orderservice.controller;

import com.niyava.ecom.orderservice.dto.response.OrderResponse;
import com.niyava.ecom.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

@Validated
@CrossOrigin(origins = {"http://localhost:3080", "http://localhost:3090", "http://localhost:8080", "http://localhost:8090"})
@RestController
@RequestMapping("/v1")
public class UserOrderController {
    private OrderService orderService;

    @Autowired
    public UserOrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping(value = "/users/{email}/orders/{orderId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public OrderResponse getOrderByEmailAndOrderId(
            @PathVariable(value = "email") @NotEmpty(message = "User email cannot be null") String email,
            @PathVariable(value = "orderId") @Min(value = 1, message = "Incorrect order id") Long orderId) {
        return orderService.getOrderByUserEmailAndOrderId(email, orderId);
    }

    @GetMapping(value = "/users/{email}/orders", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<OrderResponse> getAllOrdersByEmail(@PathVariable @NotEmpty @Email String email) {
        return orderService.getAllOrdersByEmail(email);
    }
}
