package com.jcf.exceptions;

public class CurrencyTypeNotFoundException extends RuntimeException{

    public CurrencyTypeNotFoundException(String message) {
        super(message);
    }

    public CurrencyTypeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
