package com.niyiment.mhis.shared.exception;

import com.niyiment.mhis.validation.rule.ValidationResult;
import lombok.Getter;

import java.util.List;

/**
 * Exception thrown when business rules are violated
 */
@Getter
public class BusinessRuleViolationException extends RuntimeException {
    private final String errorCode;
    private final List<ValidationResult> violations;

    public BusinessRuleViolationException(String errorCode, String message, List<ValidationResult> violations) {
        super(message);
        this.errorCode = errorCode;
        this.violations = violations != null ? violations : List.of();
    }

    public BusinessRuleViolationException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.violations = List.of();
    }

    public BusinessRuleViolationException(String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        this.violations = List.of();
    }
}
