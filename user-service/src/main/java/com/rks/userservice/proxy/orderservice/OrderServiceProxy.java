package com.rks.userservice.proxy.orderservice;

import com.rks.userservice.proxy.orderservice.dto.OrderResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "order-service")
public interface OrderServiceProxy {

    @GetMapping("/api/v1/orders?userEmail={userEmail}")
    List<OrderResponse> getAllOrdersByEmail(@RequestParam("userEmail") String userEmail);
}
