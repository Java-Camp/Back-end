package com.jcf.exceptions;

public class IncorrectTypeException extends RuntimeException{

    public IncorrectTypeException(String message) {
        super(message);
    }

    public IncorrectTypeException(String message, Throwable cause) {
        super(message, cause);
    }
}
