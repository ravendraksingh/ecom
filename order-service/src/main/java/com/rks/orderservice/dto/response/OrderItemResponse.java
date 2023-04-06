package com.rks.orderservice.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.sql.Timestamp;

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

    private String description;

    @JsonProperty("delivery_status")
    private String deliveryStatus;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = ECOM_DATE_FORMAT)
    @JsonProperty("delivery_date")
    private Timestamp deliveryDate;
}
