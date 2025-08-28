package com.niyava.ecom.orderservice.test.unittest;

import com.niyava.ecom.orderservice.controller.UserOrderController;
import com.niyava.ecom.orderservice.dto.response.OrderResponse;
import com.niyava.ecom.orderservice.service.impl.OrderServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static com.niyava.ecom.orderservice.test.TestOrderConstants.ORDER_ID_VALID;
import static com.niyava.ecom.orderservice.test.TestOrderConstants.USER_EMAIL_VALID;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@WebMvcTest(value = UserOrderController.class)
@AutoConfigureMockMvc
public class UserOrderControllerUnitTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private OrderServiceImpl orderService;

    @Test
    public void getOrderByEmailAndOrderId() throws Exception {
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setUserEmail(USER_EMAIL_VALID);
        orderResponse.setOrderId(ORDER_ID_VALID);

        given(this.orderService.getOrderByUserEmailAndOrderId(anyString(), anyLong()))
                .willReturn(orderResponse);

        this.mvc.perform(get("/v1/users/"+USER_EMAIL_VALID+"/orders/"+ORDER_ID_VALID))
                .andExpect(status().isOk())
        .andExpectAll(
                jsonPath("$.order_id").value(ORDER_ID_VALID),
                jsonPath("$.email").value(USER_EMAIL_VALID)
        );
    }

    @Test
    public void getAllOrdersByEmail() throws Exception {
        List<OrderResponse> orderResponseList = new ArrayList<>();
        OrderResponse o1 = new OrderResponse();
        o1.setOrderId(1L);
        OrderResponse o2 = new OrderResponse();
        o2.setOrderId(2L);
        orderResponseList.add(o1);
        orderResponseList.add(o2);

        given(this.orderService.getAllOrdersByEmail(anyString())).willReturn(orderResponseList);

        this.mvc.perform(get("/v1/users/"+USER_EMAIL_VALID+"/orders"))
                .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(2)));
    }
}
