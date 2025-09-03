package com.niyiment.mhis.facility.exception;

import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class FacilityValidationException extends RuntimeException {
    private final Map<String, List<String>> validationErrors;

    public FacilityValidationException(String message, Map<String, List<String>> validationErrors) {
        super(message);
        this.validationErrors = validationErrors;
    }

    public FacilityValidationException(String message) {
        super(message);
        this.validationErrors = Map.of();
    }
}
