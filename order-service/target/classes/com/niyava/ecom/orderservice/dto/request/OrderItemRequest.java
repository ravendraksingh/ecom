package com.niyava.ecom.orderservice.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderItemRequest {

    @NotNull(message = "Product Id cannot be null")
    @JsonProperty("productid")
    private Long productId;

    @NotBlank(message = "SKU cannot be null")
    private String sku;

    @NotBlank(message = "Item name cannot be null")
    private String name;

    @Min(value = 1, message = "Item quantity must be greater than or equal to one")
    private int quantity;

    @DecimalMin(value = "0.01", message = "MRP should be greater than zero")
    private BigDecimal mrp;

    private BigDecimal discount;

    @DecimalMin(value = "0.01", message = "MRP should be greater than zero")
    private BigDecimal price;

    @NotBlank(message = "Image url cannot be null")
    @JsonProperty("image_url")
    private String imageUrl;

    private String description;
}
