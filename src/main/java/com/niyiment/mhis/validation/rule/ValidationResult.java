package com.niyiment.mhis.validation.rule;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;


/**
 * Represents the result of a business rule validation
 */
@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ValidationResult {
    private final boolean valid;
    private final String errorCode;
    private final String errorMessage;
    private final String fieldName;
    private final Object rejectedValue;

    public static ValidationResult success() {
        return new ValidationResult(true, null, null, null, null);
    }

    public static ValidationResult failure(String errorCode, String errorMessage, String fieldName, Object rejectedValue) {
        return new ValidationResult(false, errorCode, errorMessage, fieldName, rejectedValue);
    }

    public static ValidationResult failure(String errorCode, String errorMessage) {
        return new ValidationResult(false, errorCode, errorMessage, null, null);
    }

    public boolean isFailure() {
        return !valid;
    }
}
