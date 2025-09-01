package com.niyiment.mhis.facility.exception;

public class FacilityOperationException extends RuntimeException{
    public FacilityOperationException(String message) {
        super(message);
    }

    public FacilityOperationException(String message, Throwable cause) {
        super(message, cause);
    }

}
