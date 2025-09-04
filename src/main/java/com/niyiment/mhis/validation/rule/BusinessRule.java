package com.niyiment.mhis.validation.rule;

/**
 * Generic business rule interface for validation logic
 * @param <T>
 */
public interface BusinessRule<T> {
    boolean isApplicable(T data);
    ValidationResult validate(T date);
    String getRuleName();
    int getPriority();
}
