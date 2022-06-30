package com.rks.orderservice.exceptions;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Setter
@Getter
@Data
@Entity(name = "serviceErrors")
@Table(name = "serviceerrors")
public class ServiceError {
    @Id
    private String error_code;
    private String error_message;
    private String exception_name;
    private String response_code;
    private String service_name;
    private String status;
    private String description;
}
