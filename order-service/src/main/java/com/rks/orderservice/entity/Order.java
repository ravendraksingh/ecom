package com.rks.orderservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rks.orderservice.converters.CustomHashMapConverter;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

    @Column(name = "total_quantity", length = 2)
    private int totalQuantity;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Item> items = new ArrayList<>();

    @Column(name = "order_status", length = 10)
    private String orderStatus;

    @Column(name = "payment_status", length = 10)
    private String paymentStatus;

    @Column(name = "payment_date")
    private Timestamp paymentDate;

    @Column(name = "customer", length = 500)
    @Convert(converter = CustomHashMapConverter.class)
    private Map<String, String> customer;

    @Column(name = "device", length = 500)
    @Convert(converter = CustomHashMapConverter.class)
    private Map<String, String> device;

    @Column(name = "delivery_address", length = 500)
    private String deliveryAddress;

    @Column(name = "payment_method", length = 10)
    private String paymentMethod;

    @Column(name = "created_date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private Timestamp createdDate;

    @Column(name = "updated_date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private Timestamp updatedDate;

    @Version
    private int version;

    public void addItem(Long productId, String sku, String name, int quantity,
                        BigDecimal mrp, BigDecimal discount, BigDecimal price,
                        String imageUrl, String description,
                        String deliveryStatus) {
        Item newItem = new Item();
        newItem.setProductId(productId);
        newItem.setSku(sku);
        newItem.setName(name);
        newItem.setQuantity(quantity);
        newItem.setMrp(mrp);
        newItem.setDiscount(discount);
        newItem.setPrice(price);
        newItem.setImageUrl(imageUrl);
        newItem.setDescription(description);
        newItem.setDeliveryStatus(deliveryStatus);

        newItem.setOrder(this);
        if (items == null) {
            items = new ArrayList<>();
        }
        items.add(newItem);
    }

    @Override
    public String toString() {
        return "Order{" +
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
                ", paymentDate=" + paymentDate +
                ", createdDate=" + createdDate +
                ", updatedDate=" + updatedDate +
                '}';
    }
}
