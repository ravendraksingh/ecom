package com.niyava.ecom.orderservice.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.niyava.ecom.orderservice.validation.IsToday;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.niyava.ecom.orderservice.constants.OrderServiceConstants.*;
import static com.niyava.ecom.orderservice.constants.OrderServiceErrorMessages.*;


@ToString
@Getter
@Setter
public class OrderRequest {
    @IsToday(message = ERR_MSG_INVALID_ORDER_DATE_TODAY)
    @DateTimeFormat(pattern = ECOM_DATE_FORMAT)
    @JsonProperty(ORDER_DATE)
    private LocalDate orderDate;

    @Valid
    private List<OrderItemRequest> items = new ArrayList<>();

    @NotBlank(message = ERR_MSG_INVALID_EMAIL)
    @Email(message = ERR_MSG_INVALID_EMAIL)
    @JsonProperty(USER_EMAIL)
    private String userEmail;

    @DecimalMin(value = "0.01", message = ERR_MSG_INVALID_TOTAL_MRP_GT1)
    @JsonProperty(TOTAL_MRP)
    private BigDecimal totalMRP;

    @JsonProperty(TOTAL_SAVING)
    private BigDecimal totalSaving;

    @DecimalMin(value = "0.01", message = ERR_MSG_INVALID_NET_AMT_GT1)
    @JsonProperty(NET_AMOUNT)
    private BigDecimal netAmount;

    @Min(value = 1, message = ERR_MSG_INVALID_ORDER_TOTAL_QTY_GT1)
    @JsonProperty(TOTAL_QTY)
    private int totalQuantity;

    private Map<String, String> customer;
    private Map<String, String> device;

    @JsonProperty(DELIVERY_ADDRESS)
    private String deliveryAddress;

    @JsonProperty(PAYMENT_METHOD)
    private String paymentMethod;

}
