package com.jcf.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Getter
@AllArgsConstructor
public class CustomApiException {

    private final String reasonPhrase;
    private final int code;
    private final String message;
    private final HttpStatus httpStatus;
    private final ZonedDateTime timestamp;



}
