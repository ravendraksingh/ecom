package com.rks.orderservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@Data
@Entity(name = "orders")
@Table(name = "orders")
public class Order {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id", nullable = false)
    private Long id;

    @Column(name = "order_date")
    private Date orderDate;

    @Column(name="user_email")
    private String userEmail;

    @Column(name = "net_amount")
    private BigDecimal netAmount;

    @Column(name = "total_mrp")
    private BigDecimal totalMRP;

    @Column(name = "total_saving")
    private BigDecimal totalSaving;

    @Column(name = "total_quantity")
    private int totalQuantity;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Item> items = new ArrayList<>();

    @Column(name = "order_status")
    private String orderStatus;

    @Column(name = "payment_status")
    private String paymentStatus;

    @Column(name = "created_date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private Date createdDate;

    @Column(name = "updated_date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private Date updatedDate;

    public void addItem(String sku, String name, int quantity,
                        BigDecimal mrp, BigDecimal discount, BigDecimal price,
                        String imageUrl) {
        Item newItem = new Item();
        newItem.setSku(sku);
        newItem.setName(name);
        newItem.setQuantity(quantity);
        newItem.setMrp(mrp);
        newItem.setDiscount(discount);
        newItem.setPrice(price);
        newItem.setImageUrl(imageUrl);
        newItem.setOrder(this);
        if (items == null) {
            items = new ArrayList<>();
        }
        items.add(newItem);
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", orderDate=" + orderDate +
                ", userEmail='" + userEmail + '\'' +
                ", netAmount=" + netAmount +
                ", totalMRP=" + totalMRP +
                ", totalSaving=" + totalSaving +
                ", totalQuantity=" + totalQuantity +
                ", items=" + items +
                ", orderStatus='" + orderStatus + '\'' +
                ", paymentStatus='" + paymentStatus + '\'' +
                ", createdDate=" + createdDate +
                ", updatedDate=" + updatedDate +
                '}';
    }
}
