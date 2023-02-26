package com.rks.orderservice.converters;

import com.rks.orderservice.entity.Order;
import com.rks.orderservice.dto.request.OrderItemRequest;
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
        context.getDestination().setNetAmount(context.getSource().getNetAmount());
        context.getDestination().setTotalMRP(context.getSource().getTotalMRP());
        context.getDestination().setTotalSaving(context.getSource().getTotalSaving());
        context.getDestination().setTotalQuantity(context.getSource().getTotalQuantity());
        context.getDestination().setOrderStatus(StatusEnum.ORDER_CREATED.getStatus());
        context.getDestination().setPaymentStatus(StatusEnum.PAYMENT_PENDING.getStatus());
        for (OrderItemRequest i : context.getSource().getItems()) {
            context.getDestination().addItem(i.getSku(), i.getName(),
                    i.getQuantity(), i.getMrp(), i.getDiscount(), i.getPrice(),
                    i.getImageUrl(), i.getDescription());
        }
        return context.getDestination();
    }
}
