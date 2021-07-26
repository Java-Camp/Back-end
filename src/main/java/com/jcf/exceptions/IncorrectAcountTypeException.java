package com.jcf.exceptions;

public class IncorrectAcountTypeException extends IncorrectTypeException{

    public IncorrectAcountTypeException(String message) {
        super(message);
    }

    public IncorrectAcountTypeException(String message, Throwable cause) {
        super(message, cause);
    }
}
