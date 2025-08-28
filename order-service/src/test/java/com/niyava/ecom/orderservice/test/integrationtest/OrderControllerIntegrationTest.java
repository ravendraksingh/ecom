package com.niyava.ecom.orderservice.test.integrationtest;

import com.niyava.ecom.orderservice.OrderServiceApplication;
import com.niyava.ecom.orderservice.dto.response.OrderResponse;
import com.niyava.ecom.orderservice.repository.OrderRepository;
import com.niyava.ecom.orderservice.test.TestOrderFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import static com.niyava.ecom.orderservice.test.TestOrderConstants.USER_EMAIL_VALID;
import static org.junit.Assert.assertEquals;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrderServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderControllerIntegrationTest extends AbstractIntegrationTest {
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private OrderRepository orderRepository;
//    @AfterAll
//    public void cleanUp() {
//        orderRepository.deleteAll();
//    }

    @Test
    @Order(1)
    @Rollback(value = false)
    public void testCreateOrder() throws Exception {
        String uri = "/orders";
        String newOrder = TestOrderFactory.createNewOrderRequestString();
        HttpEntity<String> request = new HttpEntity(newOrder, createHeaders());
        ResponseEntity<String> response = this.restTemplate.postForEntity(createURL(uri), request, String.class);
        assertEquals(201, response.getStatusCode().value());
    }

    @Test
    @Order(99)
    public void testGetAllOrders() throws Exception {
        String uri = "/orders?email=" + USER_EMAIL_VALID;
        ResponseEntity<String> response = this.restTemplate.getForEntity(createURL(uri), String.class);
        String resJson = response.getBody();
        System.out.println(resJson);
        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    @Order(2)
    public void testCreateOrderAndVerifyOrderDetails() throws Exception {
        String uri = "/orders";
        String newOrder = TestOrderFactory.createNewOrderRequestString();
        HttpEntity<String> request = new HttpEntity(newOrder, createHeaders());
        ResponseEntity<OrderResponse> response = this.restTemplate.postForEntity(createURL(uri), request, OrderResponse.class);
        assertEquals(201, response.getStatusCode().value());
        assertEquals(response.getBody().getUserEmail(), USER_EMAIL_VALID);
        assertEquals(response.getBody().getItems().size(), 1);
    }

    private String createURL(String uri) {
        return "http://localhost:" + port + "/v1/" + uri;
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        return headers;
    }

}
