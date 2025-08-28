package com.niyava.ecom.orderservice.controller;

import com.niyava.ecom.orderservice.dto.request.OrderRequest;
import com.niyava.ecom.orderservice.dto.request.UpdateOrderRequest;
import com.niyava.ecom.orderservice.dto.response.OrderResponse;
import com.niyava.ecom.orderservice.service.OrderService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Validated
@CrossOrigin(origins = {"http://localhost:3080", "http://localhost:3090", "http://localhost:8080", "http://localhost:8090"})
@RestController
@RequestMapping("/v1")
public class OrderController {
    public static final Logger logger = LoggerFactory.getLogger(OrderController.class);
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Get order details for orderId
     * @param orderId
     * @return com.response.dto.orderservice.ecom.OrderResponse
     */
    @GetMapping(value = "/orders/{orderId}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public OrderResponse getOrder(@PathVariable(value = "orderId") @Min(value=1, message="Order id must be greater than or equal to {value}") Long orderId) {
        return orderService.getOrderById(orderId);
    }

    /**
     * Create new order API
     * @return com.response.dto.orderservice.ecom.OrderResponse
     */
    @PostMapping(
            value = "/orders",
            consumes = "application/json",
            produces = "application/json"
    )
    @ResponseStatus(code = HttpStatus.CREATED)
    public OrderResponse createOrder(@RequestBody @Valid OrderRequest request) {
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

    @GetMapping("/ordersbypage")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderResponse> getOrdersPaginated(@RequestParam String email, Pageable pageRequest) {
        return orderService.getOrdersPaginated(email, pageRequest);
    }
}
