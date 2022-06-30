package com.rks.orderservice.integrationtests;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rks.orderservice.domain.Item;
import com.rks.orderservice.dto.request.OrderRequest;
import com.rks.orderservice.dto.response.OrderItem;
import com.rks.orderservice.dto.response.OrderResponse;
import com.rks.orderservice.mappers.OrderMapper;
import com.rks.orderservice.service.OrderService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class OrderControllerIT {

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderMapper orderMapper;

    private static Date myDate;
    private static OrderItem item1; //= new OrderItem(90123L, "iPhone 12S", 1, BigDecimal.valueOf(50000L));
    private static OrderResponse mockOrder;// = new OrderResponse(139L,myDate, "ravendraksingh@gmail.com", "created", "pending", BigDecimal.valueOf(50000L), Arrays.asList(item1));
    private static List<OrderResponse> mockOrderList;// = new ArrayList<OrderResponse>();
    private static OrderRequest mockCreateOrderRequestPojo;
    private static String expectedOrderResponse;
    private static String expectedOrderListResponse;
    private static String mockCreateOrderRequestStr;

    private static String defaultOrderUrl;
    private static RestTemplate restTemplate;
    private static HttpHeaders headers;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static JSONObject orderJsonObject;

    @BeforeAll
    static void init() throws ParseException {
        myDate = new SimpleDateFormat("dd-MM-yyyy").parse("22-06-2022");
        item1 = new OrderItem(90123L, "iPhone 12S", 1, BigDecimal.valueOf(50000L));
        mockOrder = new OrderResponse(139L,myDate,
                "ravendraksingh@gmail.com",
                "created", "pending", BigDecimal.valueOf(50000L), Arrays.asList(item1));

        mockOrderList = new ArrayList<>();
        mockOrderList.add(mockOrder);
        String mockCreateOrderRequestStr = "{" +
                "  \"email\": \"ravendraksingh@gmail.com\"," +
                "  \"items\": [" +
                "    {" +
                "      \"name\": \"Macbook Air 2021 Model\"," +
                "      \"price\": 120000," +
                "      \"quantity\": 1" +
                "    }" +
                "  ]," +
                "  \"order_date\": \"23-06-2022\"" +
                "}";

        Item tempItem = new Item();
        tempItem.setName("Laptop");
        tempItem.setQuantity(1);
        tempItem.setPrice(BigDecimal.valueOf(130940L));
        List<Item> itemList = new ArrayList<>();
        itemList.add(tempItem);
        mockCreateOrderRequestPojo = new OrderRequest();
        mockCreateOrderRequestPojo.setOrderDate(myDate);
        mockCreateOrderRequestPojo.setUserEmail("ravendraksingh@gmail.com");
        mockCreateOrderRequestPojo.setItems(itemList);

        expectedOrderResponse = "{" +
                "    \"order_id\": 139," +
                "    \"order_date\": \"22-06-2022\"," +
                "    \"email\": \"ravendraksingh@gmail.com\"," +
                "    \"order_status\": \"created\" " +
                " }";

        expectedOrderListResponse = "[" + expectedOrderResponse + "]";
    }



    @BeforeAll
    public static void runBeforeAllTestMethods() throws Exception {
        defaultOrderUrl = "http://localhost:8200/api/v1/orders";
        restTemplate = new RestTemplate();
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        orderJsonObject = new JSONObject();
        orderJsonObject.put("email", "ravendraksingh@gmail.com");
        orderJsonObject.put("order_date", "23-06-2022");

        JSONObject itemJson = new JSONObject();
        itemJson.put("name", "Apple Macbook Pro 2022");
        itemJson.put("quantity", 1);
        itemJson.put("price", 123000);
        JSONArray itemsListJson = new JSONArray();
        itemsListJson.put(itemJson);
        orderJsonObject.put("items", itemsListJson);
//        System.out.println("--*--*-- printing values --*--*--");
//        System.out.println(orderJsonObject);
    }

    @Test
    public void testCreateOrder() throws Exception {
        HttpEntity<String> httpRequest = new HttpEntity<>(orderJsonObject.toString(), headers);
        String orderObjectAsJsonStr =
                restTemplate.postForObject(defaultOrderUrl, httpRequest, String.class);
        JsonNode root = objectMapper.readTree(orderObjectAsJsonStr);
        System.out.println("--*--*-- printing values --*--*--");
        System.out.println(root);
        System.out.println(root.path("name").asText());
        System.out.println(root.path("order_id") + " :: " + root.path("order_date") +
                root.path("items").path("unit_price"));
        assertNotNull(root);
        assertNotNull(root.path("name").asText());
    }
}
