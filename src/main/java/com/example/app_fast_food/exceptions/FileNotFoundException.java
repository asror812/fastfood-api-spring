package com.example.app_fast_food.exceptions;

public class FileNotFoundException extends RuntimeException {
    private static final String MESSAGE = "%s file in path `%s` not found ";

    public FileNotFoundException(String fileName, String path) {
        super(String.format(MESSAGE, fileName, path));
    }
}
