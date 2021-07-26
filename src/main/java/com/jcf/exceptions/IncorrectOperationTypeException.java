package com.jcf.exceptions;

public class IncorrectOperationTypeException extends IncorrectTypeException{

    public IncorrectOperationTypeException(String message) {
        super(message);
    }

    public IncorrectOperationTypeException(String message, Throwable cause) {
        super(message, cause);
    }
}
