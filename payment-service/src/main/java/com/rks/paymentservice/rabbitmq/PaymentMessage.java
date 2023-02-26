package com.rks.paymentservice.rabbitmq;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@Getter
@Setter
@NoArgsConstructor
public class PaymentMessage implements Serializable {
    private Long transactionid;
    private String orderid;
    private String mercid;
    private Timestamp transaction_date;
    private BigDecimal surcharge;
    private BigDecimal discount;
    private BigDecimal net_amount;

    @Override
    public String toString() {
        return "PaymentMessage{" +
                "transactionid=" + transactionid +
                ", orderid='" + orderid + '\'' +
                ", mercid='" + mercid + '\'' +
                ", transaction_date=" + new SimpleDateFormat("yyyy-MM-dd").format(transaction_date) +
                ", surcharge=" + surcharge +
                ", discount=" + discount +
                ", net_amount=" + net_amount +
                '}';
    }
}
