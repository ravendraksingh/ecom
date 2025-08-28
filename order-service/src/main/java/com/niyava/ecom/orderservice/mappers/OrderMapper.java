package com.niyava.ecom.orderservice.mappers;

import com.niyava.ecom.orderservice.converters.OrderRequestToOrderConverter;
import com.niyava.ecom.orderservice.converters.OrderToOrderResponseConverter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper extends ModelMapper {
    public OrderMapper() {
        OrderToOrderResponseConverter c1 = new OrderToOrderResponseConverter();
        OrderRequestToOrderConverter c2 = new OrderRequestToOrderConverter();
        this.addConverter(c1);
        this.addConverter(c2);
    }
}
