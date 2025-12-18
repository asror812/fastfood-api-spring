package com.example.app_fast_food.exception;

public class InvalidImageTypeException extends RuntimeException {
    private static final String MESSAGE = "This image type `%s` not supported";

    public InvalidImageTypeException(String type) {
        super(MESSAGE.formatted(type));
    }
}
