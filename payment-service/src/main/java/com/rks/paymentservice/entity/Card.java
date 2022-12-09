package com.rks.paymentservice.entity;


import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * "card":{
 *         "cardtoken":"a452fc97ea0229da87b35ea71d3acb812ac",
 *                 "masked_value":"424242xxxxxx1234",
 *                 "network":"visa",
 *                 "issuer":"HDFC Bank",
 *                 "type":"debit",
 *                 "number":"4242424242421234",
 *                 "expiry_month":"12",
 *                 "expiry_year" :"2049",
 *                 "holder_name": "Tejas"
 *     }
 */
@Entity
public class Card {
    @Id
    private String card_number;
    private String cardtoken;
    private String masked_value;
    private String network;
    private String issuer;
    private String type;
    private int expiry_month;
    private int expiry_year;
    private String holder_name;
}
