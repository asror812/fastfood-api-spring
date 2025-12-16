package com.example.app_fast_food.exception;

public class InvalidCredentialsException extends RuntimeException {
    private static final String MESSAGE = "Invalid credentials: username: `%s`, password: `%s`";

    public InvalidCredentialsException(String username, String password) {
        super(MESSAGE.formatted(username, password));
    }
}
