package com.jcf.exceptions;

public class UnsupportedMediaTypeException extends  RuntimeException {

    public UnsupportedMediaTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnsupportedMediaTypeException(Throwable cause) {
        super(cause);
    }
}
