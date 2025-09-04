package com.niyiment.mhis.shared.exception;


import lombok.Getter;

@Getter
public class InvalidOperationException extends RuntimeException {
    private final String operationType;
    private final String reason;

    public InvalidOperationException(String message) {
        super(message);
        this.operationType = null;
        this.reason = null;
    }

    public InvalidOperationException(String operationType, String reason) {
        super(String.format("Invalid operation: %s. Reason: %s", operationType, reason));
        this.operationType = operationType;
        this.reason = reason;
    }
}
