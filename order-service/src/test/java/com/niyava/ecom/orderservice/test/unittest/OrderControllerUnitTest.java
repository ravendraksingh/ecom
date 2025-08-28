package com.niyava.ecom.orderservice.test.unittest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.niyava.ecom.orderservice.controller.OrderController;
import com.niyava.ecom.orderservice.dto.request.OrderRequest;
import com.niyava.ecom.orderservice.service.OrderService;
import com.niyava.ecom.orderservice.test.TestOrderFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Ravendra Kumar Singh [ravendraksingh@gmail.com]
 * @version 1.0
 * @since 02 Dec 2023
 */

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = OrderController.class)
@AutoConfigureMockMvc
public class OrderControllerUnitTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;
    @MockBean
    OrderService orderService;

    @Test
    public void whenNullEmail_thenReturn400() throws Exception {
        OrderRequest orderRequest = TestOrderFactory.createNewOrderRequest();
        orderRequest.setUserEmail(null);
        this.mockMvc.perform(post("/v1/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(orderRequest)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}
