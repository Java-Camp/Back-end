package com.jcf.exceptions.handler;

import com.jcf.exceptions.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionsHandler extends ResponseEntityExceptionHandler {

    private Map<String, Object> getBody(String errorMessage, Exception e){
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", errorMessage);
        body.put("debug message", e.getMessage());
        return body;
    }

    @ExceptionHandler(value = {IncorrectAcountTypeException.class, IncorrectOperationTypeException.class})
    public ResponseEntity<Object> handleIncorrectTypeException (IncorrectTypeException e){
        return new ResponseEntity<>(getBody("Incorrect Operation Type", e), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = LockedAccessException.class)
    public ResponseEntity<Object> handleLockedAccessException (LockedAccessException e){
        return new ResponseEntity<>(getBody("Locked Access", e), HttpStatus.LOCKED);
    }

    @ExceptionHandler(value = CurrencyTypeNotFoundException.class)
    public ResponseEntity<Object> handleCurrencyTypeNotFoundException(CurrencyTypeNotFoundException e){
        return new ResponseEntity<>(getBody("Currency Type Not Found", e), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = UnprocessableEntityException.class)
    public ResponseEntity<Object> handleUnprocessableEntityException (UnprocessableEntityException e){
        return new ResponseEntity<>(getBody("Unprocessable Entity", e), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(value = UnsupportedMediaTypeException.class)
    public ResponseEntity<Object> handleUnsupportedMediaTypeException (UnsupportedMediaTypeException e){
        return new ResponseEntity<>(getBody("Unsupported Media Type", e), HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @ExceptionHandler(value = RequestTimeOutException.class)
    public ResponseEntity<Object> handleRequestTimeOutException (RequestTimeOutException e){
        return new ResponseEntity<>(getBody("Request Time Out", e), HttpStatus.REQUEST_TIMEOUT);
    }

    @ExceptionHandler(value = SQLException.class)
    public ResponseEntity<Object> handleSqlException (SQLException e){
        return new ResponseEntity<>(getBody("Incorrect Data Entry", e), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFoundEx(EntityNotFoundException e) {
        return new ResponseEntity<>(getBody("Entity Not Found", e), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = FieldIsNullException.class)
    protected ResponseEntity<Object> handleFieldIsNulelException(FieldIsNullException e) {
        return new ResponseEntity<>(getBody("Field Is Empty", e), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ServiceNotWorkingException.class)
    protected ResponseEntity<Object> handleAllExceptions(ServiceNotWorkingException e) {
        return new ResponseEntity<>(getBody("Service Not Working", e), HttpStatus.SERVICE_UNAVAILABLE);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(getBody("Incorrect Data", e), status);
    }

//    @ExceptionHandler(Exception.class)
//    protected ResponseEntity<Object> handleAllExceptions(Exception e) {
//        return new ResponseEntity<>(getBody("Exception", e), HttpStatus.INTERNAL_SERVER_ERROR);
//    }

    @ExceptionHandler(value = InternalAuthenticationServiceException.class)
    protected ResponseEntity<Object> handleInternalAuthenticationServiceException(InternalAuthenticationServiceException e) {
        return new ResponseEntity<>(getBody("User not found", e), HttpStatus.NOT_FOUND);
    }
}
