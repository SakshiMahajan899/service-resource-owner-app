package com.backend.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class ApiError {
    private boolean success;
    private String message;
    private int statusCode;

    public ApiError(String message, int statusCode) {
        this.success = false;
        this.message = message;
        this.statusCode = statusCode;
    }
}

