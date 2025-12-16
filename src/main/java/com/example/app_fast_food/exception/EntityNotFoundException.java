package com.example.app_fast_food.exception;

public class EntityNotFoundException extends RuntimeException {
    private static final String MESSAGE = "%s entity with id `%s` not found";

    public EntityNotFoundException(String entityName, String id) {
        super(String.format(MESSAGE, entityName, id));
    }
}
