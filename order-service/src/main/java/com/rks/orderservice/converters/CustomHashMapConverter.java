package com.rks.orderservice.converters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@Converter
public class CustomHashMapConverter implements AttributeConverter<Map<String, Object>, String> {

    @Override
    public String convertToDatabaseColumn(Map<String, Object> customer) {
        String customJson = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            customJson = objectMapper.writeValueAsString(customer);
        } catch (JsonProcessingException e) {
            log.error("JSON writing error", e);
        }
        return customJson;
    }

    @Override
    public Map<String, Object> convertToEntityAttribute(String customJSON) {
        Map<String, Object> customObject = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            customObject = objectMapper.readValue(customJSON, new TypeReference<HashMap<String, Object>>() {});
        } catch (final IOException e) {
            log.error("JSON reading error", e);
        }
        return customObject;
    }
}
