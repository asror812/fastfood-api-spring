package com.example.app_fast_food.exceptions;

import java.time.LocalDateTime;

public class OtpLimitExitedException extends RuntimeException {
    private static final String MESSAGE = "Otp limit is reached: `%s`. Please try  after `%s`";

    public OtpLimitExitedException(int count, LocalDateTime reTryTime) {
        super(String.format(MESSAGE, count, reTryTime));
    }
}
