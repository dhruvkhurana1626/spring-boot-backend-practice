package com.example.demo.exception;

public class SellerNotFound extends RuntimeException {
    public SellerNotFound(String message) {
        super(message);
    }
}
