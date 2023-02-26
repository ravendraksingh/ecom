package com.rks.orderservice.rabbitmq;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderMessage implements Serializable {
    public static final long serialVersionUID = -7872297983617727925L;
    private Long order_id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date order_date;
    private String email;
    private String order_status;
    private String payment_status;
    private BigDecimal net_amount;
}
