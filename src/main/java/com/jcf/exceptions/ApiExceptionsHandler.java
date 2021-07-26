package com.jcf.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionsHandler {

    @ExceptionHandler(value = {SQLException.class})
    public ResponseEntity<Object> handleSqlException (SQLException e){

        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        CustomApiException exception = new CustomApiException(
                httpStatus.getReasonPhrase(),
                httpStatus.value(),
                e.getMessage(),
                httpStatus,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(exception, httpStatus);
    }

    @ExceptionHandler(value = {RequestTimeOutException.class})
    public ResponseEntity<Object> handleRequestTimeOutException (RequestTimeOutException e){

        HttpStatus httpStatus = HttpStatus.REQUEST_TIMEOUT;

        CustomApiException exception = new CustomApiException(
                httpStatus.getReasonPhrase(),
                httpStatus.value(),
                e.getMessage(),
                httpStatus,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(exception, httpStatus);
    }

    @ExceptionHandler(value = {UnsupportedMediaTypeException.class})
    public ResponseEntity<Object> handleUnsupportedMediaTypeException (UnsupportedMediaTypeException e){

        HttpStatus httpStatus = HttpStatus.UNSUPPORTED_MEDIA_TYPE;

        CustomApiException exception = new CustomApiException(
                httpStatus.getReasonPhrase(),
                httpStatus.value(),
                e.getMessage(),
                httpStatus,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(exception, httpStatus);
    }
    @ExceptionHandler(value = {UnprocessableEntityException.class})
    public ResponseEntity<Object> handleUnprocessableEntityException (UnprocessableEntityException e){

        HttpStatus httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;

        CustomApiException exception = new CustomApiException(
                httpStatus.getReasonPhrase(),
                httpStatus.value(),
                e.getMessage(),
                httpStatus,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(exception, httpStatus);
    }
    @ExceptionHandler(value = {LockedAccessException.class})
    public ResponseEntity<Object> handleLockedAccessException (LockedAccessException e){

        HttpStatus httpStatus = HttpStatus.LOCKED;

        CustomApiException exception = new CustomApiException(
                httpStatus.getReasonPhrase(),
                httpStatus.value(),
                e.getMessage(),
                httpStatus,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(exception, httpStatus);
    }
    @ExceptionHandler(value = {IncorrectAcountTypeException.class, IncorrectOperationTypeException.class})
    public ResponseEntity<Object> handleIncorrectTypeException (IncorrectTypeException e){

        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        CustomApiException exception = new CustomApiException(
                httpStatus.getReasonPhrase(),
                httpStatus.value(),
                e.getMessage(),
                httpStatus,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(exception, httpStatus);
    }





}
