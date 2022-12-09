package com.rks.gateway.config;

import com.rks.gateway.filter.AuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

//@Component
//@Order(2)
public class LoadBalancedRoutesConfig {

    @Autowired
    AuthenticationFilter authenticationFilter;

    @Bean
    public RouteLocator orderRoute(RouteLocatorBuilder builder) {

        return builder.routes()
                .route(r -> r.path("/public/api/v1/orders/**")
                    .filters(f -> f.rewritePath("/public/api/v1/orders", "/api/v1/orders")
                            .filter(authenticationFilter)
                    )
                    //.uri("lb://order-service")
                        .uri("http://localhost:8200")
                )
                .build();
    }

    @Bean
    public RouteLocator localBalancedRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
//                .route(r -> r.path("/api/v1/auth/**")
//                        .uri("lb://user-service"))
                .route(r -> r.path("/api/products/**")
                        .uri("lb://catalog-service"))
                .route(r -> r.path("/api/categories/**")
                        .uri("lb://catalog-service"))
//                .route(r -> r.path("/api/orders/**")
//                        .uri("lb://order-service"))
                .build();
    }
}
