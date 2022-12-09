package com.rks.orderservice.dto.helper;

import com.rks.orderservice.entity.Order;
import com.rks.orderservice.dto.request.OrderRequest;
import com.rks.orderservice.dto.response.OrderResponse;
import com.rks.orderservice.mappers.OrderMapper;
import org.springframework.stereotype.Component;

@Component
public class OrderDTOHelper {

    private static OrderMapper orderMapper;
    static {
        orderMapper = new OrderMapper();
    }

    public static OrderResponse createOrderResponseForOrder(Order order) {
        OrderResponse orderResponse = new OrderResponse();
        orderMapper.map(order, orderResponse);
        return orderResponse;
    }

    public static Order createOrderForOrderRequest(OrderRequest orderRequest) {
        Order order = new Order();
        orderMapper.map(orderRequest, order);
        return order;
    }
}
