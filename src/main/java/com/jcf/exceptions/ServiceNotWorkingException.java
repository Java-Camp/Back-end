package com.jcf.exceptions;

public class ServiceNotWorkingException extends RuntimeException{
    public ServiceNotWorkingException(String service) {
        super(String.format("Service %s is not working", service));
    }
}
