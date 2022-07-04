package com.rks.orderserviceproxy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class OrderserviceProxyApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderserviceProxyApplication.class, args);
    }

}
