package com.rks.userservice.proxy.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem implements Serializable {

    private static final long serialVersionUID = -3070748634377399498L;
    private Long item_id;
    private String name;
    private int quantity;
    private BigDecimal unit_price;

    @Override
    public String toString() {
        return "{" +
                "id=" + item_id +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", price=" + unit_price +
                '}';
    }
}
