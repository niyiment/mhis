package com.niyiment.mhis.shared.exception;

import com.niyiment.mhis.facility.exception.DuplicateFacilityCodeException;
import com.niyiment.mhis.facility.exception.FacilityNotFoundException;
import com.niyiment.mhis.facility.exception.FacilityValidationException;
import com.niyiment.mhis.shared.dto.CustomApiResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(FacilityNotFoundException.class)
  public ResponseEntity<CustomApiResponse<Void>> handleFacilityNotFoundException(
      FacilityNotFoundException ex) {
    log.error("Facility not found: {}", ex.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(CustomApiResponse.error("Facility not found", ex.getMessage()));
  }

  @ExceptionHandler(DuplicateFacilityCodeException.class)
  public ResponseEntity<CustomApiResponse<Void>> handleDuplicateFacilityCodeException(
      DuplicateFacilityCodeException ex) {
    log.error("Duplicate facility code: {}", ex.getMessage());
    return ResponseEntity.status(HttpStatus.CONFLICT)
        .body(CustomApiResponse.error("Duplicate facility code", ex.getMessage()));
  }

  @ExceptionHandler(FacilityValidationException.class)
  public ResponseEntity<CustomApiResponse<Map<String, List<String>>>>
      handleFacilityValidationException(FacilityValidationException ex) {
    log.error("Facility validation error: {}", ex.getMessage());

    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(
            CustomApiResponse.<Map<String, List<String>>>builder()
                .success(false)
                .message("Validation failed")
                .data(ex.getValidationErrors())
                .error(ex.getMessage())
                .build());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<CustomApiResponse<Map<String, String>>>
      handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
    log.error("Validation error: {}", ex.getMessage());

    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult()
        .getFieldErrors()
        .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(
            CustomApiResponse.<Map<String, String>>builder()
                .success(false)
                .message("Validation failed")
                .data(errors)
                .build());
  }

  @ExceptionHandler(BindException.class)
  public ResponseEntity<CustomApiResponse<Map<String, String>>> handleBindingException(
      BindException ex) {
    log.error("Binding error: {}", ex.getMessage());
    Map<String, String> errors =
        ex.getBindingResult().getFieldErrors().stream()
            .collect(
                Collectors.toMap(
                    FieldError::getField,
                    FieldError::getDefaultMessage,
                    (existing, replacement) -> existing));

    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(
            CustomApiResponse.<Map<String, String>>builder()
                .success(false)
                .message("Binding failed")
                .data(errors)
                .build());
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<CustomApiResponse<Void>> handleMethodArgumentTypeMismatchException(
      MethodArgumentTypeMismatchException ex) {
    log.error("Method argument type mismatch: {}", ex.getMessage());
    String error =
        String.format(
            "Invalid value '%s' for parameter '%s'. Expected type: %s",
            ex.getValue(), ex.getName(), ex.getRequiredType().getSimpleName());

    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(CustomApiResponse.error("Invalid request data", error));
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<CustomApiResponse<Void>> handleIllegalArgumentException(
      IllegalArgumentException ex) {
    log.error("Illegal argument: {}", ex.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(CustomApiResponse.error("Invalid request data", ex.getMessage()));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<CustomApiResponse<Void>> handleException(Exception ex) {
    log.error("Internal server error: {}", ex.getMessage());
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(CustomApiResponse.error("Internal server error", ex.getMessage()));
  }
}
