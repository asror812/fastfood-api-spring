package com.example.app_fast_food.exception;

public class OtpEarlyResentException extends RuntimeException {
    private static final String MESSAGE = "Please try after: 0:%d";

    public OtpEarlyResentException(long resentTime) {
        super(String.format(MESSAGE, resentTime));
    }
}