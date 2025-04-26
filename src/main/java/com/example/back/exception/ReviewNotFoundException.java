package com.example.back.exception;

public class ReviewNotFoundException extends RuntimeException {
    
    public ReviewNotFoundException(Integer id) {
        super("無法找到評價: " + id);
    }
    
    public ReviewNotFoundException(String message) {
        super(message);
    }
}