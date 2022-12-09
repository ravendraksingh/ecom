package com.rks.orderservice.service;

import com.rks.orderservice.dto.response.OrderResponse;

public interface SecureOrderService {
    OrderResponse getOrderById(Long orderId);
}
