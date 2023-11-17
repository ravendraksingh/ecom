package com.rks.orderservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "items")
@Table(name = "items")
public class Item {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "item_id")
    private Long id;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "sku", length = 10, nullable = false)
    private String sku;

    @Column(name = "item_name", length = 255, nullable = false)
    private String name;

    @Column(name = "quantity", length = 3, nullable = false)
    private int quantity;

    @Column(name = "mrp")
    private BigDecimal mrp;

    @Column(name = "discount")
    private BigDecimal discount;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "description", length=500)
    private String description;

    @Column(name = "delivery_status")
    private String deliveryStatus;

    @Column(name = "delivery_date")
    private Timestamp deliveryDate;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(name = "created_date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private Date createdDate;

    @Column(name = "updated_date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private Date updatedDate;

    @Override
    public String toString() {
        return "Item{" +
                "productId='" + productId + '\'' +
                ", sku='" + sku + '\'' +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", mrp=" + mrp +
                ", discount=" + discount +
                ", price=" + price +
                '}';
    }
}
