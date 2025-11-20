package com.example.app_fast_food.exceptions;

public class FileSizeLimitExceedException extends RuntimeException {
    private static final String MESSAGE = "%s file size exceed limit. Limit is %d";

    public FileSizeLimitExceedException(String fileName, Long limitSize) {
        super(String.format(MESSAGE, fileName, limitSize));
    }
}
