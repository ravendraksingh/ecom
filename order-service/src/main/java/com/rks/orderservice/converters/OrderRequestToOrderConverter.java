package com.rks.orderservice.converters;

import com.rks.orderservice.domain.Item;
import com.rks.orderservice.domain.Order;
import com.rks.orderservice.dto.request.OrderRequest;
import com.rks.orderservice.util.StatusEnum;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

@Component
public class OrderRequestToOrderConverter implements Converter<OrderRequest, Order> {

    @Override
    public Order convert(MappingContext<OrderRequest, Order> context) {
        context.getDestination().setOrderDate(context.getSource().getOrderDate());
        context.getDestination().setUserEmail(context.getSource().getUserEmail());
        context.getDestination().setOrderStatus(StatusEnum.ORDER_CREATED.getStatus());
        context.getDestination().setPaymentStatus(StatusEnum.PAYMENT_PENDING.getStatus());
        for (Item i : context.getSource().getItems()) {
            context.getDestination().addItem(i.getName(), i.getQuantity(), i.getPrice());
        }
        return context.getDestination();
    }
}
