package com.niyiment.mhis.shared.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.extern.slf4j.Slf4j;


@Converter
@Slf4j
public class JsonbConverter implements AttributeConverter<Object, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Object attribute) {
        if (attribute == null) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException ex) {
            log.error("Error converting object to JSON string", ex);
            return null;
        }
    }

    @Override
    public Object convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.trim().isEmpty()) {
            return null;
        }
        try {
            return objectMapper.readValue(dbData, Object.class);
        } catch (JsonProcessingException ex) {
            log.error("Error converting JSON string to object", ex);
            return null;
        }
    }

}
