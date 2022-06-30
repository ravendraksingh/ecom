package com.rks.orderservice.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rks.orderservice.domain.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderItemRequest {
    @JsonIgnore
    private Long id;
    private String name;
    private int quantity;
    private BigDecimal price;
    @JsonIgnore
    private Order order;
}
