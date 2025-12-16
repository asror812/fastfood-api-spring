package com.example.app_fast_food.exception;

public class AlreadyExistsException extends RuntimeException {
    private static final String MESSAGE = "%s with %s: `%s` already exists";

    public AlreadyExistsException(String className, String attribute, String value) {
        super(String.format(MESSAGE, className, attribute, value));
    }
}