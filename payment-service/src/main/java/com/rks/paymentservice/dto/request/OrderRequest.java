package com.rks.paymentservice.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;


import static com.rks.paymentservice.constants.Constant.TXN_DATE_FORMAT;

@ToString
@Setter
@Getter
public class OrderRequest {
    private String orderid;
    private String entity;
    private String mercid;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = TXN_DATE_FORMAT)
    private Timestamp order_date;
    private BigDecimal amount;
    private BigDecimal amount_due;
    private BigDecimal amount_paid;
    private String currency;
    private String ru;
    private String itemcode;
    private String settlement_lob;
    private Map<String, String> customer;
    private Map<String, String> device;
    private List<String> payment_categories;
}
