package com.example.demo.exception;

public class PhoneAlreadyUsed extends RuntimeException {
    public PhoneAlreadyUsed(String message) {
        super(message);
    }
}
