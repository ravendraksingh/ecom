package com.rks.orderservice.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.math.BigDecimal;

import static com.rks.orderservice.constants.OrderServiceConstants.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {

    @JsonProperty(ITEM_ID)
    private Long id;

    @Size(min = 1, max = 255, message = "Item name length should be less than 255")
    @NotBlank(message = "Item name cannot be null")
    @JsonProperty(ITEM_NAME)
    private String name;

    @Max(value = 100, message = "Item quantity cannot be greater than 100")
    @Min(value = 1, message = "Item quantity must be greater than zero")
    @JsonProperty(ITEM_QUANTITY)
    private int quantity;

    @DecimalMin(value = "0.01", message = "Unit price should be greater than zero")
    @Pattern(regexp = "^\\s*(?=.*[1-9])\\d*(?:\\.\\d{1,2})?\\s*$", message = "Amount must be a positive number with maximal 2 decimal places")
    @JsonProperty(value = ITEM_UNIT_PRICE, required = true)
    private BigDecimal price;
}
