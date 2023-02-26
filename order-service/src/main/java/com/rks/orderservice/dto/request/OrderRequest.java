package com.rks.orderservice.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.rks.orderservice.constants.OrderServiceConstants.ORDER_DATE;
import static com.rks.orderservice.constants.OrderServiceConstants.USER_EMAIL;


@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class OrderRequest {

    @NotNull(message = "Order date cannot be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @JsonProperty(ORDER_DATE)
    private Date orderDate;

    @Valid
    private List<OrderItemRequest> items = new ArrayList<>();

    @Size(min = 1, max = 255, message = "Email length cannot be more than 255")
    @NotBlank(message = "User email cannot be null")
    @Email(message = "User email is invalid")
    @JsonProperty(USER_EMAIL)
    private String userEmail;

    @Min(value = 1, message = "Total MRP must be greater than or equal to 1")
    @JsonProperty("total_mrp")
    private BigDecimal totalMRP;

    @JsonProperty("total_saving")
    private BigDecimal totalSaving;

    @Min(value = 1, message = "Net amount must be greater than or equal to 1")
    @JsonProperty("net_amount")
    private BigDecimal netAmount;

    @Min(value = 1, message = "Total quantity must be greater than or equal to 1")
    @JsonProperty("total_quantity")
    private int totalQuantity;
}
