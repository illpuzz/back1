package com.example.back.exception;

public class ReviewException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    
    public ReviewException(String message) {
        super(message);
    }
    
    public ReviewException(String message, Throwable cause) {
        super(message, cause);
    }
}