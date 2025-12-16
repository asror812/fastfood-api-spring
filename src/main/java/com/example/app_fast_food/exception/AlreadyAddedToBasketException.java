package com.example.app_fast_food.exception;

public class AlreadyAddedToBasketException extends RuntimeException {
    public AlreadyAddedToBasketException(String message) {
        super(message);
    }
}
