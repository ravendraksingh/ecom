package com.rks.paymentservice.clients.orderservice;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "order-service", url = "localhost:8200")
public interface OrderServiceClientFeign {
//    @PutMapping("/order-service/int/v1/orders/{orderId}")
//    OrderResponse updateOrder(@PathVariable("orderId") Long orderId, @RequestBody UpdateOrderRequest request);
}
