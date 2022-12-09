package com.rks.orderservice.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import static com.rks.orderservice.constants.OrderServiceConstants.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemResponse {
    private String sku;
    private String name;
    private int quantity;
    private BigDecimal mrp;
    private BigDecimal discount;

    @JsonProperty(ITEM_UNIT_PRICE)
    private BigDecimal price;

    @JsonProperty("image_url")
    private String imageUrl;

    @Override
    public String toString() {
        return "{" +
                "name='" + name + '\'' +
                ", quantity=" + quantity +
                ", mrp=" + mrp +
                ", discount=" + discount +
                ", price=" + price +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
