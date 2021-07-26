package com.jcf.exceptions;

public class LockedAccessException extends RuntimeException {


    public LockedAccessException(String message) {

        super(message);

    }
    public LockedAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
