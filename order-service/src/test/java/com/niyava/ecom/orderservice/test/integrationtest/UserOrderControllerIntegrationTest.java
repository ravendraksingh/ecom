package com.niyava.ecom.orderservice.test.integrationtest;

import com.niyava.ecom.orderservice.controller.UserOrderController;
import com.niyava.ecom.orderservice.service.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = UserOrderController.class)
@AutoConfigureMockMvc
public class UserOrderControllerIntegrationTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private UserOrderController userOrderController;
    @MockBean
    private OrderService orderService;

    private static final String VALID_USER_EMAIL = "john.doe@mail.com";
    private static final String INVALID_USER_EMAIL = "-";

    @Test
    public void whenGetOrderByEmailAndInValidUser_thenBadRequest() throws Exception {
        mvc.perform(get("/v1/users/" + INVALID_USER_EMAIL + "/orders"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
    @Test
    public void whenGetOrderByEmailAndValidUser_thenCorrectResponse() throws Exception {
        mvc.perform(get("/v1/users/" + VALID_USER_EMAIL + "/orders"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
