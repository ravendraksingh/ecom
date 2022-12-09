package com.rks.orderserviceproxy;


import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RibbonClient(name = "order-service")
@FeignClient(name = "order-service")
public interface OrderServiceProxy {

    @GetMapping("/api/v1/orders/{id}")
    ResponseEntity<?> getOrder(@PathVariable("id") Long id);
}
