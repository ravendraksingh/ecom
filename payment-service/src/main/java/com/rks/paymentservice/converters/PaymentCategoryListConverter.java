package com.rks.paymentservice.converters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;
import java.util.List;

@Slf4j
@Component
@Converter(autoApply = true)
public class PaymentCategoryListConverter implements AttributeConverter<List<String>, String> {
    @Override
    public String convertToDatabaseColumn(List<String> paymentCategoryList) {
        String paymentCategoryListJson = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            paymentCategoryListJson = objectMapper.writeValueAsString(paymentCategoryList);
        } catch (JsonProcessingException e) {
            log.error("JSON writing error", e);
        }
        return paymentCategoryListJson;
    }

    @Override
    public List<String> convertToEntityAttribute(String paymentCategoryListJson) {
        List<String> paymentCategoryList = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            paymentCategoryList = objectMapper.readValue(paymentCategoryListJson, new TypeReference<List<String>>() {});
        } catch (final IOException e) {
            log.error("JSON reading error", e);
        }
        return paymentCategoryList;
    }
}
