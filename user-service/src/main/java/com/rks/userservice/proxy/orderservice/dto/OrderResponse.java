package com.rks.userservice.proxy.orderservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderResponse implements Serializable {
    private static final long serialVersionUID = -6368654398204667606L;

    @NotNull
    @JsonProperty("order_id")
    private Long orderId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @JsonProperty("order_date")
    private Date orderDate;

    @JsonProperty("email")
    private String email;

    @JsonProperty("order_status")
    private String orderStatus;

    @JsonProperty("payment_status")
    private String paymentStatus;

    @Pattern(regexp = "^\\s*(?=.*[1-9])\\d*(?:\\.\\d{1,2})?\\s*$", message = "Amount must be a positive number with maximal 2 decimal places")
    @JsonProperty(value = "order_amount", required = true)
    private BigDecimal orderAmount;

    @JsonProperty("items")
    private List<OrderItem> items;

    public void addItem(Long id, String name, int quantity, BigDecimal price) {
        OrderItem newItem = new OrderItem();
        newItem.setItem_id(id);
        newItem.setName(name);
        newItem.setQuantity(quantity);
        newItem.setUnit_price(price);

        if (items == null) {
            items = new ArrayList<>();
        }
        items.add(newItem);
    }

    @Override
    public String toString() {
        return "{" +
                "orderId=" + orderId +
                ", orderDate=" + orderDate +
                ", email='" + email + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", paymentStatus='" + paymentStatus + '\'' +
                ", orderAmount=" + orderAmount +
                ", items=" + items +
                '}';
    }
}
