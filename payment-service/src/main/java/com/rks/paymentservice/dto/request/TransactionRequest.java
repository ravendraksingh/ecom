package com.rks.paymentservice.dto.request;

import com.rks.paymentservice.converters.CustomHashMapConverter;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Convert;
import java.math.BigDecimal;
import java.util.Map;

@ToString
@Setter
@Getter
public class TransactionRequest {
//    private String entity;
    private Long pgorderid;
    private String orderid;
    private String mercid;
//    private Timestamp transaction_date;
    private BigDecimal amount;
//    private BigDecimal surcharge;
//    private BigDecimal discount;
//    private BigDecimal charge_amount;
    private String currency;
    private String ru;
    private String itemcode;
    private String settlement_lob;
    //    private String status;

    @Convert(converter = CustomHashMapConverter.class)
    private Map<String, String> customer;

    @Convert(converter = CustomHashMapConverter.class)
    private Map<String, String> device;
    private String payment_method_type; //"card"
//    private String bankid;
//    private String bank_ref_no;
//    private String auth_status;
//    private String txn_process_type;
//    private String transaction_error_code; // "PGSuccess"
//    private String transaction_error_desc; // "Transaction successful"
//    private String transaction_error_type; // "Success"
//    private String authcode; //"123456"
//    private String network_transactiionid; //"123412341234"
//    private String eci; //"02"

    /**
     * "card":{
     *     "cardtoken":"a452fc97ea0229da87b35ea71d3acb812ac",
     *     "masked_value":"424242xxxxxx1234",
     *     "network":"visa",
     *     "issuer":"HDFC Bank",
     *     "type":"debit",
     *     "number":"4242424242421234",
     *     "expiry_month":"12",
     *     "expiry_year" :"2049",
     *     "holder_name": "Tejas"
     *   }
     */
    @Convert(converter = CustomHashMapConverter.class)
    private Map<String, String> card;
    private String next_step;
    private String email;
//    private Timestamp created_at;
//    private Timestamp updated_at;
}
