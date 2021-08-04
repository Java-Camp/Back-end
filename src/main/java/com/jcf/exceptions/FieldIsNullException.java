package com.jcf.exceptions;

public class FieldIsNullException extends IllegalArgumentException{
    public FieldIsNullException(String name) {
        super(String.format("Field with name \"%s\" not found", name));
    }
}
