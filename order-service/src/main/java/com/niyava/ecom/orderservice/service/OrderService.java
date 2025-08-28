package com.niyava.ecom.orderservice.service;

import com.niyava.ecom.orderservice.dto.request.OrderRequest;
import com.niyava.ecom.orderservice.dto.request.UpdateOrderRequest;
import com.niyava.ecom.orderservice.dto.response.OrderResponse;
import com.niyava.ecom.orderservice.entity.Order;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface OrderService {
    List<OrderResponse> findAllOrders();
    List<OrderResponse> getAllOrdersByEmail(String email);
    OrderResponse getOrderByUserEmailAndOrderId(String email, Long id);
    List<OrderResponse> getAllOrdersByUserEmailAndOrderDate(String email, LocalDate orderDate);
    List<Order> findByOrderStatus(String status);
    OrderResponse getOrderById(Long orderId);
    void updateOrderStatus(Long orderId, String orderStatus);
    OrderResponse createNewOrder(OrderRequest orderRequest);
    void deleteAllOrders();
    OrderResponse updateOrder(Long orderId, UpdateOrderRequest request);
    List<OrderResponse> getOrdersPaginated(String email, Pageable pageRequest);
}
