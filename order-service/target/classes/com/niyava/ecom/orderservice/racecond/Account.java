package com.niyava.ecom.orderservice.racecond;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Table(name = "accounts")
@Data
@Entity
public class Account {
    @Id
    @Column(name = "account_number")
    private Long accountNumber;
    @Column(name = "name")
    private String name;
    @Column(name = "balance")
    private BigDecimal balance;
    @Column(name = "last_txn_type")
    private String last_txn_type;
    @Column(name = "last_txn_amt")
    private BigDecimal last_txn_amt;
    @Column(name = "version")
    private int version;
    @Column(name = "created_date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private Timestamp createdDate;
    @Column(name = "updated_date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private Timestamp updatedDate;
}
