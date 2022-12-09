package com.rks.paymentservice.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * {
 *   "first_name": "John",
 *   "last_name": "Doe",
 *   "email": "abc@xyz.com",
 *   "email_alt": "def@xyz.com",
 *   "mobile": "9820098200",
 *   "mobile_alt": "9820198201",
 * }
 */

//@Data
//@Entity
//@Table(name = "customer")
public class Customer {
    //@Id
    //private Long id;
    private String first_name;
    private String last_name;
    private String email;
    private String email_alt;
    private String mobile;
    private String mobile_alt;
}
