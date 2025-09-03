package com.niyiment.mhis.shared.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomApiResponse<T> {
    private boolean success;
    private String message;
    private T data;
    private String error;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();

    public static <T> CustomApiResponse<T> success(String message, T data) {
        return CustomApiResponse.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static <T> CustomApiResponse<T> success(T data) {
        return success("Operation completed successfully", data);
    }

    public static <T> CustomApiResponse<T> error(String message) {
        return CustomApiResponse.<T>builder()
                .success(false)
                .message("Operation failed")
                .error(message)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static <T> CustomApiResponse<T> error(String message, String error) {
        return CustomApiResponse.<T>builder()
                .success(false)
                .message(message)
                .error(error)
                .timestamp(LocalDateTime.now())
                .build();
    }
}
