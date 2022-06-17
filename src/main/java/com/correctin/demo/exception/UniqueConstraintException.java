package com.correctin.demo.exception;

public class UniqueConstraintException extends RuntimeException{
    public UniqueConstraintException(String message) {
        super(message);
    }
}
