package com.rks.orderservice.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.rks.orderservice.domain.Item;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.rks.orderservice.constants.OrderServiceConstants.ORDER_DATE;
import static com.rks.orderservice.constants.OrderServiceConstants.USER_EMAIL;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {

    @NotNull(message = "Order date cannot be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @JsonProperty(ORDER_DATE)
    private Date orderDate;

    //@NotEmpty(message = "List of items cannot be null")
    @Valid
    private List<Item> items = new ArrayList<>();

    //@Valid
    //private List<OrderItem> items = new ArrayList<>();

    @Size(min = 1, max = 255, message = "Email length cannot be more than 255")
    @NotBlank(message = "User email cannot be null")
    @Email(message = "User email is invalid")
    @JsonProperty(USER_EMAIL)
    private String userEmail;
}
