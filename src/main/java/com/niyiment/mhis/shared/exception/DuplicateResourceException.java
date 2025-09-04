package com.niyiment.mhis.shared.exception;


import lombok.Getter;

@Getter
public class DuplicateResourceException extends RuntimeException{
    private final String fieldName;
    private final Object duplicateValue;

    public DuplicateResourceException(String fieldName, Object duplicateValue) {
        super(String.format("%s already exists with value: %s", fieldName, duplicateValue));
        this.fieldName = fieldName;
        this.duplicateValue = duplicateValue;
    }

    public DuplicateResourceException(String message, String fieldName, Object duplicateValue) {
        super(message);
        this.fieldName = fieldName;
        this.duplicateValue = duplicateValue;
    }

    public DuplicateResourceException(String message) {
        super(message);
        this.fieldName = null;
        this.duplicateValue = null;
    }
}
