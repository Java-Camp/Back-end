package com.jcf.exceptions;

public class EntityNotFoundException extends RuntimeException{
    public EntityNotFoundException(Long id) {
        super(String.format("Entity with id %d not found", id));
    }

    public EntityNotFoundException(String value) {
        super(String.format("Entity with value %s not found", value));
    }
}
