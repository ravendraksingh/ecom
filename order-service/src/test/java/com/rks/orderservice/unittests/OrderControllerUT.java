package com.rks.orderservice.unittests;

import com.rks.orderservice.controller.OrderController;
import com.rks.orderservice.dto.request.OrderRequest;
import com.rks.orderservice.dto.response.OrderItem;
import com.rks.orderservice.dto.response.OrderResponse;
import com.rks.orderservice.service.OrderService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ContextConfiguration(classes = {org.springframework.cloud.sleuth.autoconfig.TraceAutoConfiguration.class,
        com.rks.orderservice.controller.OrderController.class
})
@WebMvcTest(value = OrderController.class)
public class OrderControllerUT {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private OrderService orderService;

    static Date myDate;
    static OrderItem item1; //= new OrderItem(90123L, "iPhone 12S", 1, BigDecimal.valueOf(50000L));
    static OrderResponse mockOrder;// = new OrderResponse(139L,myDate, "ravendraksingh@gmail.com", "created", "pending", BigDecimal.valueOf(50000L), Arrays.asList(item1));
    static List<OrderResponse> mockOrderList;// = new ArrayList<OrderResponse>();
    static String mockCreateOrderRequest;
    static String expectedOrderResponse;
    static String expectedOrderListResponse;

    @BeforeAll
    static void init() throws ParseException {
        myDate = new SimpleDateFormat("dd-MM-yyyy").parse("22-06-2022");
        item1 = new OrderItem(90123L, "iPhone 12S", 1, BigDecimal.valueOf(50000L));
        mockOrder = new OrderResponse(139L,myDate,
                "ravendraksingh@gmail.com",
                "created", "pending", BigDecimal.valueOf(50000L), Arrays.asList(item1));

        mockOrderList = new ArrayList<>();
        mockOrderList.add(mockOrder);
        mockCreateOrderRequest = "{" +
                "  \"email\": \"ravendraksingh@gmail.com\"," +
                "  \"items\": [" +
                "    {" +
                "      \"name\": \"Macbook Air 2021 Model\"," +
                "      \"price\": 120000," +
                "      \"quantity\": 1" +
                "    }" +
                "  ]," +
                "  \"order_date\": \"22-06-2022\"" +
                "}";

        expectedOrderResponse = "{" +
                "    \"order_id\": 139," +
                "    \"order_date\": \"22-06-2022\"," +
                "    \"email\": \"ravendraksingh@gmail.com\"," +
                "    \"order_status\": \"created\" " +
                " }";

        expectedOrderListResponse = "[" + expectedOrderResponse + "]";
    }

    @Test
    public void getOrderById() throws Exception {
        Mockito.when(orderService.getOrderById(Mockito.anyLong())).thenReturn(mockOrder);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/orders/139").accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        //System.out.println("result.getResponse().getContentAsString() :: " + result.getResponse().getContentAsString());
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        JSONAssert.assertEquals(expectedOrderResponse, result.getResponse().getContentAsString(), false);
        //Assert.assertNotNull(result.getResponse());
    }

    @Test
    public void createOrder() throws Exception {
        Mockito.when(orderService.createNewOrder(Mockito.any(OrderRequest.class))).thenReturn(mockOrder);
        RequestBuilder rb = MockMvcRequestBuilders.post("/api/v1/orders")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mockCreateOrderRequest);
        MvcResult result = mockMvc.perform(rb).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        JSONAssert.assertEquals(expectedOrderResponse, result.getResponse().getContentAsString(), false);
    }

    @Test
    public void getAllOrdersByUserEmail() throws Exception {
        Mockito.when(orderService.getAllOrdersByEmail(Mockito.anyString())).thenReturn(mockOrderList);
        RequestBuilder rb = MockMvcRequestBuilders.get("/api/v1/users/ravendraksingh@gmail.com/orders")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(rb).andReturn();
        MockHttpServletResponse response = result.getResponse();
//        System.out.println("-*-*- printing values -*-*-");
//        System.out.println(result);
//        System.out.println(response.getContentAsString() + "\n" + response.getStatus());
//        System.out.println(response);
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        JSONAssert.assertEquals(expectedOrderListResponse, result.getResponse().getContentAsString(), false);
    }
}
