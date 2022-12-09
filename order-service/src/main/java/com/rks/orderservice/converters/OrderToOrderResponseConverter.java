package com.rks.orderservice.converters;

import com.rks.orderservice.entity.Item;
import com.rks.orderservice.entity.Order;
import com.rks.orderservice.dto.response.OrderResponse;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class OrderToOrderResponseConverter implements Converter<Order, OrderResponse> {
    final static Logger logger = LoggerFactory.getLogger(OrderToOrderResponseConverter.class);

    @Override
    public OrderResponse convert(MappingContext<Order, OrderResponse> context) {
        context.getDestination().setOrderId(context.getSource().getId());
        context.getDestination().setUserEmail(context.getSource().getUserEmail());
        context.getDestination().setTotalMRP(context.getSource().getTotalMRP());
        context.getDestination().setTotalSaving(context.getSource().getTotalSaving());
        context.getDestination().setNetAmount(context.getSource().getNetAmount());
        context.getDestination().setOrderDate(context.getSource().getOrderDate());
        context.getDestination().setOrderStatus(context.getSource().getOrderStatus());
        context.getDestination().setPaymentStatus(context.getSource().getPaymentStatus());

        if (context.getSource().getItems() != null) {
            for (Item i : context.getSource().getItems()) {
                context.getDestination().addItem(i.getId(), i.getName(),
                        i.getQuantity(), i.getPrice(), i.getMrp(), i.getDiscount(),
                        i.getImageUrl(), i.getSku());
            }
        }
        return context.getDestination();
    }
}
