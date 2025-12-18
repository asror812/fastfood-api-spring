package com.example.app_fast_food.exception;

public class UserBasketNotFoundException extends RuntimeException {

    public UserBasketNotFoundException(String message) {
        super(message);
    }
}
