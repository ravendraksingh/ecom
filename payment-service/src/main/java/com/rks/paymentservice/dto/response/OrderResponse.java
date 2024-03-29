package com.rks.paymentservice.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@ToString
public class OrderResponse extends BaseResponse implements Serializable {
//public class OrderResponse extends RepresentationModel<OrderResponse> implements Serializable {
    private static final long serialVersionUID = 5354400233738724981L;
    private String entity;
    private Long pgorderid;
    private String orderid;
    private String mercid;
    private String order_date;
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
    private String next_step;
    private String status;
    private String created_at;
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX", timezone="GMT+05:30")
//    private Date createdAt;
}
