package com.example.app_fast_food.exception;

public class FileNotFoundException extends RuntimeException {
    private static final String MESSAGE = "%s file in path `%s` not found ";

    public FileNotFoundException(String fileName, String path) {
        super(String.format(MESSAGE, fileName, path));
    }

    public FileNotFoundException(String message){
        super(message);
    }
}
