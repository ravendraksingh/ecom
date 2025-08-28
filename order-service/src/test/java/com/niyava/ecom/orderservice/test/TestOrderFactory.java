package com.niyava.ecom.orderservice.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.niyava.ecom.orderservice.dto.request.OrderItemRequest;
import com.niyava.ecom.orderservice.dto.request.OrderRequest;
import com.niyava.ecom.orderservice.entity.Item;
import com.niyava.ecom.orderservice.entity.Order;
import com.niyava.ecom.orderservice.util.OrderDateUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.niyava.ecom.orderservice.test.TestOrderConstants.*;

/**
 * @author Ravendra Kumar Singh [ravendraksingh@gmail.com]
 * @version 1.0
 * @since 02 Dec 2023
 */
public class TestOrderFactory {
    public static OrderRequest createNewOrderRequest() {
        OrderItemRequest item = new OrderItemRequest();
        item.setProductId(123L);
        item.setName("Coffee Mug");
        item.setDescription("Coffee Mug");
        item.setSku("SKU-001");
        item.setMrp(new BigDecimal(500));
        item.setDiscount(new BigDecimal(300));
        item.setPrice(new BigDecimal(200));
        item.setQuantity(1);
        item.setImageUrl("http://localhost:8000/coffeemug.jpg");

        List<OrderItemRequest> itemList = new ArrayList<>();
        itemList.add(item);

        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setUserEmail(USER_EMAIL_VALID);
        orderRequest.setOrderDate(OrderDateUtil.toLocalDate(new Date()));
        orderRequest.setItems(itemList);
        orderRequest.setTotalMRP(new BigDecimal(500));
        orderRequest.setTotalQuantity(1);
        orderRequest.setTotalSaving(new BigDecimal(300));
        orderRequest.setNetAmount(new BigDecimal(200));
        return orderRequest;
    }
    public static String createNewOrderRequestString() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(createNewOrderRequest());
    }
    public static Order createNewOrder() {
        Order order = new Order();
        Item item = new Item();

        item.setSku("SKU-001");
        item.setName("Coffee Mug");
        item.setDescription("Coffee Mug");
        item.setProductId(901L);
        item.setMrp(new BigDecimal(500));
        item.setDiscount(new BigDecimal(300));
        item.setPrice(new BigDecimal(300));
        item.setImageUrl("http://localhost:8000/coffeemug.jpg");
        item.setDeliveryStatus(DELIVERY_STATUS_PENDING);
        item.setOrder(order);

        List<Item> itemList = new ArrayList<>();
        itemList.add(item);

        //order.setId(1L);
        order.setOrderDate(OrderDateUtil.toLocalDate(new Date()));
        order.setUserEmail(USER_EMAIL_VALID);
        order.setOrderStatus(ORDER_STATUS_PENDING);
        order.setItems(itemList);
        order.setTotalMRP(new BigDecimal(500));
        order.setTotalSaving(new BigDecimal(300));
        order.setNetAmount(new BigDecimal(200));
        order.setTotalQuantity(1);

        return order;
    }
}
