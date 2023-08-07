package com.rks.orderservice.service;

import com.rks.orderservice.entity.Order;
import com.rks.orderservice.dto.request.OrderRequest;
import com.rks.orderservice.dto.request.UpdateOrderRequest;
import com.rks.orderservice.dto.response.OrderResponse;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface OrderService {
    List<OrderResponse> findAllOrders();
    List<OrderResponse> getAllOrdersByEmail(String email);
    OrderResponse getOrderByUserEmailAndOrderId(String email, Long id);
    List<OrderResponse> getAllOrdersByUserEmailAndOrderDate(String email, Date orderDate);
    List<Order> findByOrderStatus(String status);
    OrderResponse getOrderById(Long orderId);
    void updateOrderStatus(Long orderId, String orderStatus);
    OrderResponse createNewOrder(OrderRequest orderRequest);
    void deleteAllOrders();
    OrderResponse updateOrder(Long orderId, UpdateOrderRequest request);
    List<OrderResponse> getOrdersPaginated(String email, Pageable pageRequest);
}
