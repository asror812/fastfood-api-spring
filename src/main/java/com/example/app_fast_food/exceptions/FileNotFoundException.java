package com.example.app_fast_food.exceptions;

public class FileNotFoundException extends RuntimeException {
    private static final String MESSAGE = "%s file not found in path: %s";

    public FileNotFoundException(String fileName, String path) {
        super(String.format(MESSAGE, fileName, path));
    }
}
