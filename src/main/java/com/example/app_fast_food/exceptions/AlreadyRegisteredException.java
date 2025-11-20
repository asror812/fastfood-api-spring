package com.example.app_fast_food.exceptions;

public class AlreadyRegisteredException extends RuntimeException {
    private static final String MESSAGE = "This phone number: %s is already registered";

    public AlreadyRegisteredException(String phoneNumber) {
        super(MESSAGE.formatted(phoneNumber));
    }
}
