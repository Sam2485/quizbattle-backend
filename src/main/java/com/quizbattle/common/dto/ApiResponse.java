package com.quizbattle.common.dto;

import java.time.Instant;

public class ApiResponse<T> {

    private boolean success;
    private T data;
    private String error;
    private Instant timestamp;



    private ApiResponse(
            boolean success,
            T data,
            String error
    ) {
        this.success = success;
        this.data = data;
        this.error = error;
        this.timestamp = Instant.now();
    }



    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, data, null);
    }



    public static <T> ApiResponse<T> error(String error) {
        return new ApiResponse<>(false, null, error);
    }



    public boolean isSuccess() {
        return success;
    }

    public T getData() {
        return data;
    }

    public String getError() {
        return error;
    }

    public Instant getTimestamp() {
        return timestamp;
    }
}