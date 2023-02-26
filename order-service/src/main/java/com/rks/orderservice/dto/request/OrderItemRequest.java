package com.rks.orderservice.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderItemRequest {
    @NotEmpty(message = "SKU cannot be null")
    private String sku;

    @NotEmpty(message = "Item name cannot be null")
    private String name;
    private int quantity;

    @DecimalMin(value = "0.01", message = "MRP should be greater than zero")
    private BigDecimal mrp;

    private BigDecimal discount;

    @DecimalMin(value = "0.01", message = "MRP should be greater than zero")
    private BigDecimal price;

    @NotEmpty(message = "Image url cannot be null")
    @JsonProperty("image_url")
    private String imageUrl;

    private String description;
}
