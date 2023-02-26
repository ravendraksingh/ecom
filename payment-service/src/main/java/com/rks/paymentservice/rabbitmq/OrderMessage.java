package com.rks.paymentservice.rabbitmq;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderMessage {
//    public static final long serialVersionUID = -7872297983617727925L;
    private Long order_id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date order_date;
    private String email;
    private String order_status;
    private String payment_status;
    private BigDecimal net_amount;

    @Override
    public String toString() {
        return "{" +
                "order_id=" + order_id +
                ", order_date='" + new SimpleDateFormat("yyyy-MM-dd").format(order_date) + '\'' +
                ", email='" + email + '\'' +
                ", order_status='" + order_status + '\'' +
                ", payment_status='" + payment_status + '\'' +
                ", net_amount=" + net_amount +
                '}';
    }
}


