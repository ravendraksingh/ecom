package com.rks.orderservice.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportOrderSummary {
    private String order_status;
    private String payment_status;
    private int count;
    private BigDecimal net_amount;
}
