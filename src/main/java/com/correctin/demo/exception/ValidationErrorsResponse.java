package com.correctin.demo.exception;

import lombok.Data;

import java.time.ZonedDateTime;
import java.util.Map;

@Data
public class ValidationErrorsResponse {

    private final int statusCode;
    private final ZonedDateTime timestamp;
    private final Map<String, String> errors;

    public ValidationErrorsResponse(int statusCode, ZonedDateTime timestamp, Map<String, String> errors) {
        this.statusCode = statusCode;
        this.timestamp = timestamp;
        this.errors = errors;
    }
}
