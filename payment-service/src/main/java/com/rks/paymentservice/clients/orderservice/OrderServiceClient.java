package com.rks.paymentservice.clients.orderservice;

import com.rks.paymentservice.dto.response.OrderResponse;

public interface OrderServiceClient {
    OrderResponse getOrderDetails(Long orderId);

    OrderResponse getOrderDetailsWithJwt(Long orderId);

    OrderResponse getOrderDetailsWithJwtToken(Long orderId, String jwtToken);
}
