package com.rks.orderservice.rabbitmq;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import static com.rks.orderservice.constants.OrderServiceConstants.TXN_DATE_FORMAT;

@Getter
@Setter
@NoArgsConstructor
public class PaymentMessage {
    private Long transactionid;
    private String orderid;
    private String mercid;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
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
                ", transaction_date='" + new SimpleDateFormat(TXN_DATE_FORMAT).format(transaction_date) + '\'' +
                ", surcharge=" + surcharge +
                ", discount=" + discount +
                ", net_amount=" + net_amount +
                '}';
    }
}
