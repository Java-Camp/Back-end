package com.jcf.exceptions;

public class RequestTimeOutException extends RuntimeException{

    public RequestTimeOutException(String message) {
        super(message);
    }

    public RequestTimeOutException(String message, Throwable cause) {
        super(message, cause);
    }
}
