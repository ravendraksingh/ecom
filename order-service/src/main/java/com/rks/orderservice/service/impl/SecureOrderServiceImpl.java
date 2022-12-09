package com.rks.orderservice.service.impl;

import auth.HmacUtils;
import com.rks.mcommon.exceptions.BadRequestException;
import com.rks.orderservice.entity.Order;
import com.rks.orderservice.entity.helpers.OrderRequestHeaders;
import com.rks.orderservice.dto.response.OrderResponse;
import com.rks.orderservice.exceptions.ServiceErrorFactory;
import com.rks.orderservice.mappers.OrderMapper;
import com.rks.orderservice.repository.OrderRepository;
//import com.rks.orderservice.security.SecUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

import java.nio.charset.StandardCharsets;

import static com.rks.orderservice.constants.OrderServiceConstants.*;
import static com.rks.orderservice.constants.OrderServiceErrorCodes.ORDER_NOT_FOUND;
import static com.rks.orderservice.constants.OrderServiceErrorMessages.FAILED;

@Slf4j
@Service
public class SecureOrderServiceImpl {
    private OrderRepository orderRepository;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    public SecureOrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public ResponseEntity processGetOrderById(Long orderId, HttpServletRequest request) throws Exception {
        log.debug("Fetching order for orderId: {}", orderId);

        OrderRequestHeaders orderRequestHeaders = validateAndGetOrderRequestHeaders(request);

        Order order = orderRepository.findById(orderId).orElseThrow(() -> ServiceErrorFactory.getNamedException(ORDER_NOT_FOUND));
        OrderResponse orderResponse = new OrderResponse();

        orderMapper.map(order, orderResponse);

        String encodedOrderResponse = Base64.encodeBase64String(orderResponse.toString().getBytes(StandardCharsets.UTF_8));
        log.info("orderResponse: {}", orderResponse);
        log.info("encoded orderResponse: " + encodedOrderResponse);

//        SecUtil.signData(orderResponse.toString(), orderRequestHeaders.getClientId());

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(HEADER_RESPONSE_HMAC, getResponseHmac(request, orderResponse));
        responseHeaders.add(HEADER_TRACE_ID, orderRequestHeaders.getTraceId());
        responseHeaders.add(HEADER_CLIENT_ID, orderRequestHeaders.getClientId());

        ResponseEntity<OrderResponse> responseEntity = new ResponseEntity<>(orderResponse, responseHeaders, HttpStatus.OK);
        return responseEntity;
    }


    private String getResponseHmac(HttpServletRequest request, OrderResponse orderResponse) {
        String responseHmac = null;
        try {
            responseHmac = HmacUtils.generateResponseHmac(HttpStatus.OK.toString(), orderResponse.toString(),
                    MediaType.APPLICATION_JSON_VALUE,
                    request.getHeader(HEADER_TRACE_ID),
                    request.getHeader(HEADER_TIMESTAMP),
                    request.getHeader(HEADER_CLIENT_ID),
                    request.getHeader(HEADER_CLIENT_SECRET));
            log.info("responseHmac: {}", responseHmac);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseHmac;
    }



    private OrderRequestHeaders validateAndGetOrderRequestHeaders(HttpServletRequest request) {
        String traceId = request.getHeader(HEADER_TRACE_ID);
        if (StringUtils.isBlank(traceId)) {
            throw new BadRequestException(FAILED, 400, "Invalid traceId");
        }
        String timestamp = request.getHeader(HEADER_TIMESTAMP);
        if (StringUtils.isBlank(timestamp)) {
            throw new BadRequestException(FAILED, 400, "Invalid timestamp");
        }
        String clientId = request.getHeader(HEADER_CLIENT_ID);
        if (StringUtils.isBlank(clientId)) {
            throw new BadRequestException(FAILED, 400, "Invalid clientId");
        }
        String clientSecret = request.getHeader(HEADER_CLIENT_SECRET);
        if (StringUtils.isBlank(clientSecret)) {
            throw new BadRequestException(FAILED, 400, "Invalid clientSecret");
        }
        OrderRequestHeaders orderRequestHeaders = new OrderRequestHeaders(traceId, timestamp, clientId, clientSecret);
        return orderRequestHeaders;
    }

}
