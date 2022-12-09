package com.rks.paymentservice.entity;

import com.rks.paymentservice.converters.CustomHashMapConverter;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long pgorderid;
    private String orderid;
    private String entity;
    private String mercid;
    private Timestamp order_date;
    private BigDecimal amount;
    private BigDecimal amount_due;
    private BigDecimal amount_paid;
    private String currency;
    private String ru;
    private String itemcode;
    private String settlement_lob;

    @Convert(converter = CustomHashMapConverter.class)
    private Map<String, String> customer;

    @Convert(converter = CustomHashMapConverter.class)
    private Map<String, String> device;
    private String status;

    @Convert(converter = CustomHashMapConverter.class)
    private List<String> payment_categories ;

    private String next_step;

    //@Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private Timestamp created_at;

    //@Column(name = "updated_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false)
    private Timestamp updated_at;


}
