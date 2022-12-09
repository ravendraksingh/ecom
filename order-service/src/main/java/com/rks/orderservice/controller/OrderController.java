package com.rks.orderservice.controller;

import brave.Tracer;
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

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Api(produces = "application/json", value = "Order operations")
@Validated
@CrossOrigin(origins = {"http://localhost:3080", "http://localhost:3090", "http://localhost:8080", "http://localhost:8090"})
@RestController
@RequestMapping("/api/v1")
public class OrderController {
    public static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    private OrderService orderService;
    @Autowired
    RestTemplate restTemplate;
//    @Autowired
//    Tracer tracer;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


    /**
     * Get order details for orderId
     * @param orderId
     * @return com.rks.orderservice.dto.response.OrderResponse
     */
    @ApiOperation(value = "Get order details for an order id", response = OrderResponse.class)
    @GetMapping(value = "/orders/{orderId}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public OrderResponse getOrder(@PathVariable(value = "orderId") @Min(value=1, message="Order id must be greater than or equal to {value}") Long orderId) {
        return orderService.getOrderById(orderId);
    }

    /**
     * Create new order API
     * @return com.rks.orderservice.dto.response.OrderResponse
     */
    @ApiOperation(value = "Create a new order", response = OrderResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created a new order"),
            @ApiResponse(code = 401, message = "You are not authorized to perform this action"),
            @ApiResponse(code = 404, message = "Order not found")
    })
    @PostMapping(
            value = "/orders",
            consumes = "application/json",
            produces = "application/json"
    )
    @ResponseStatus(code = HttpStatus.CREATED)
    public OrderResponse createOrder(@RequestBody OrderRequest request) {
        return orderService.createNewOrder(request);
    }

    @GetMapping("/orders")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderResponse> getAllOrders() {
       return orderService.findAllOrders();
    }

    @PutMapping("/orders/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public OrderResponse updateOrder(@PathVariable @Min(value=1, message="Order id must be greater than or equal to {value}") Long orderId,
                                     @RequestBody UpdateOrderRequest request) {
        return orderService.updateOrder(orderId, request);
    }
}
