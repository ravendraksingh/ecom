package com.rks.orderservice.controller;

import com.google.common.base.Strings;
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
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.Date;
import java.util.List;

@Api(produces = "application/json", value = "Order operations")
@Validated
@CrossOrigin(origins = {"http://localhost:3080", "http://localhost:3090", "http://localhost:8080", "http://localhost:8090"})
@RestController
@RequestMapping("/v1")
public class OrderController {
    public static final Logger logger = LoggerFactory.getLogger(OrderController.class);
    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
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
    public OrderResponse createOrder(@Valid @RequestBody OrderRequest request) {
        return orderService.createNewOrder(request);
    }

    @GetMapping("/orders")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderResponse> getAllOrders(
            @RequestParam String email,
            @RequestParam(required = false) Long order_id,
            @RequestParam(required = false) Date order_date) {

       if (Strings.isNullOrEmpty(email)) {
           return orderService.findAllOrders();
       } else if (!Strings.isNullOrEmpty(email) && order_id>0) {
        return (List<OrderResponse>) orderService.getOrderByUserEmailAndOrderId(email, order_id);
       }
       else { return orderService.getAllOrdersByEmail(email);}
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
