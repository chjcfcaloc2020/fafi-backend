package com.chjcfcaloc2020.fafi.exception.payload;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
