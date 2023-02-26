package com.rks.paymentservice.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.rks.paymentservice.converters.CustomHashMapConverter;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Convert;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@ToString
public class TransactionResponse implements Serializable {
    private String entity;
    private Long transactionid;
    private Long pgorderid;
    private String orderid;
    private String mercid;
    private Timestamp transaction_date;
    private BigDecimal amount;
    private BigDecimal surcharge;
    private BigDecimal discount;
    private BigDecimal charge_amount;
    private String currency;
    private String ru;
    private String status;
    @Convert(converter = CustomHashMapConverter.class)
    private Map<String, String> customer;
    private String bankid;
    private String itemcode;
    private String settlement_lob;
    private String bank_ref_no;
    private String auth_status;
    private String txn_process_type;
    private String transaction_error_code; // "PGSuccess"
    private String transaction_error_desc; // "Transaction successful"
    private String transaction_error_type; // "Success"
    private String authcode; //"123456"
    private String network_transactiionid; //"123412341234"
    private String eci; //"02"
    private String payment_method_type; //"card"
    private String next_step;
    private Timestamp valid_till;
    private Timestamp created_at;
    private Timestamp updated_at;
    private String email;
}
