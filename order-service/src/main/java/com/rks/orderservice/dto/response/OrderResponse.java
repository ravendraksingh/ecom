package com.rks.orderservice.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @JsonProperty(ORDER_DATE)
    private Date orderDate;

    @JsonProperty(USER_EMAIL)
    private String userEmail;

    @JsonProperty(ORDER_STATUS)
    private String orderStatus;

    @JsonProperty(PAYMENT_STATUS)
    private String paymentStatus;

    @JsonProperty("total_mrp")
    private BigDecimal totalMRP;

    @JsonProperty("total_saving")
    private BigDecimal totalSaving;

    //@Pattern(regexp = "^\\s*(?=.*[1-9])\\d*(?:\\.\\d{1,2})?\\s*$", message = "Amount must be a positive number with maximal 2 decimal places")
    @JsonProperty("net_amount")
    private BigDecimal netAmount;

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
                " orderDate=" + orderDate +
                ", userEmail='" + userEmail + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", paymentStatus='" + paymentStatus + '\'' +
                ", netAmount=" + netAmount +
                ", totalMRP=" + totalMRP +
                ", totalSaving=" + totalSaving +
                ", items=" + items +
                '}';
    }
}
