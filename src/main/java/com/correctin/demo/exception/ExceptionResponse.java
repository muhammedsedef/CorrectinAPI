package com.correctin.demo.exception;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Data
public class ExceptionResponse {

    private final String message;
    private final int statusCode;
    private final LocalDateTime timestamp;

    public ExceptionResponse(String message, int statusCode, LocalDateTime timestamp) {
        this.message = message;
        this.statusCode = statusCode;
        this.timestamp = timestamp;
    }

}
