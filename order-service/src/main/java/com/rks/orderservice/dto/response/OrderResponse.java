package com.rks.orderservice.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.rks.orderservice.converters.CustomHashMapConverter;
import com.rks.orderservice.util.CustomTxnDateSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Convert;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.rks.orderservice.constants.OrderServiceConstants.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse implements Serializable {
    private static final long serialVersionUID = -6368654398204667606L;

    @NotNull
    @JsonProperty(ORDER_ID)
    private Long orderId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = ECOM_DATE_FORMAT)
    @JsonProperty(ORDER_DATE)
    private Date orderDate;

    @JsonProperty(USER_EMAIL)
    private String userEmail;

    @JsonProperty(ORDER_STATUS)
    private String orderStatus;

    @JsonProperty(PAYMENT_STATUS)
    private String paymentStatus;

    @JsonProperty("payment_date")
    @JsonSerialize(using = CustomTxnDateSerializer.class)
    private Timestamp paymentDate;

    @JsonProperty("total_mrp")
    private BigDecimal totalMRP;

    @JsonProperty("total_saving")
    private BigDecimal totalSaving;

    //@Pattern(regexp = "^\\s*(?=.*[1-9])\\d*(?:\\.\\d{1,2})?\\s*$", message = "Amount must be a positive number with maximal 2 decimal places")
    @JsonProperty("net_amount")
    private BigDecimal netAmount;

    @Convert(converter = CustomHashMapConverter.class)
    private Map<String, String> customer;

    @Convert(converter = CustomHashMapConverter.class)
    private Map<String, String> device;

    @JsonProperty("delivery_address")
    private String deliveryAddress;

    @JsonProperty("delivery_status")
    private String deliveryStatus;

    @JsonProperty("payment_method")
    private String paymentMethod;

    @JsonProperty(ITEMS_IN_ORDER)
    private List<OrderItemResponse> items = new ArrayList<>();


    public void addItem(Long id, String name, int quantity,
                        BigDecimal price, BigDecimal mrp, BigDecimal discount, String imageUrl,
                        String sku, String description,
                        String deliveryStatus, Timestamp deliveryDate) {
        OrderItemResponse newItem = new OrderItemResponse();
        newItem.setName(name);
        newItem.setQuantity(quantity);
        newItem.setMrp(mrp);
        newItem.setPrice(price);
        newItem.setDiscount(discount);
        newItem.setImageUrl(imageUrl);
        newItem.setSku(sku);
        newItem.setDescription(description);
        newItem.setDeliveryStatus(deliveryStatus);
        newItem.setDeliveryDate(deliveryDate);
        //newItem.setId(id);

        if (items == null) {
            items = new ArrayList<>();
        }
        items.add(newItem);
    }

    @Override
    public String toString() {
        return "{" +
                "orderId=" + orderId +
                " orderDate='" + new SimpleDateFormat(ECOM_DATE_FORMAT).format(orderDate) + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", paymentStatus='" + paymentStatus + '\'' +
                ", paymentDate='" + paymentDate + '\'' +
                ", netAmount=" + netAmount +
                ", totalMRP=" + totalMRP +
                ", totalSaving=" + totalSaving +
                ", items=" + items +
                '}';
    }
}
