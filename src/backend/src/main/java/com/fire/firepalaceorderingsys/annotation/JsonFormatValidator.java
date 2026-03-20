package com.fire.firepalaceorderingsys.annotation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

// 验证器实现
public class JsonFormatValidator implements ConstraintValidator<ValidJson, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.trim().isEmpty()) {
            return true; // 允许为空，根据业务需求调整
        }
        try {
            objectMapper.readTree(value);
            return true;
        } catch (JsonProcessingException e) {
            return false;
        }
    }
}
