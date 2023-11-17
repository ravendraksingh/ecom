package com.rks.orderservice.converters;

import com.rks.orderservice.entity.Item;
import com.rks.orderservice.entity.Order;
import com.rks.orderservice.dto.response.OrderResponse;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

import org.springframework.stereotype.Component;

@Component
public class OrderToOrderResponseConverter implements Converter<Order, OrderResponse> {

    @Override
    public OrderResponse convert(MappingContext<Order, OrderResponse> ctx) {
        ctx.getDestination().setOrderId(ctx.getSource().getId());
        ctx.getDestination().setUserEmail(ctx.getSource().getUserEmail());
        ctx.getDestination().setTotalMRP(ctx.getSource().getTotalMRP());
        ctx.getDestination().setTotalSaving(ctx.getSource().getTotalSaving());
        ctx.getDestination().setNetAmount(ctx.getSource().getNetAmount());
        ctx.getDestination().setOrderDate(ctx.getSource().getOrderDate());
        ctx.getDestination().setOrderStatus(ctx.getSource().getOrderStatus());
        ctx.getDestination().setPaymentStatus(ctx.getSource().getPaymentStatus());
        ctx.getDestination().setPaymentDate(ctx.getSource().getPaymentDate());
        ctx.getDestination().setCustomer(ctx.getSource().getCustomer());
        ctx.getDestination().setDevice(ctx.getSource().getDevice());
        ctx.getDestination().setDeliveryAddress(ctx.getSource().getDeliveryAddress());
        ctx.getDestination().setPaymentMethod(ctx.getSource().getPaymentMethod());

        if (ctx.getSource().getItems() != null) {
            for (Item i : ctx.getSource().getItems()) {
                ctx.getDestination().addItem(i.getId(), i.getProductId(), i.getName(),
                        i.getQuantity(), i.getPrice(), i.getMrp(), i.getDiscount(),
                        i.getImageUrl(), i.getSku(), i.getDescription(),
                        i.getDeliveryStatus(), i.getDeliveryDate());
            }
        }
        return ctx.getDestination();
    }
}
