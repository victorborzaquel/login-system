package com.vb.loginsystem.config;

import com.vb.loginsystem.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    private record ErrorResponse(String error) { }

    @ExceptionHandler(AccountAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse exceptionHandler(AccountAlreadyExistsException e) {
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler(AccountNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse exceptionHandler(AccountNotFoundException e) {
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler(EmailAlreadyConfirmedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse exceptionHandler(EmailAlreadyConfirmedException e) {
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler(InvalidAccountLoginException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse exceptionHandler(InvalidAccountLoginException e) {
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler(TokenExpiredException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse exceptionHandler(TokenExpiredException e) {
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler(TokenNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse exceptionHandler(TokenNotFoundException e) {
        return new ErrorResponse(e.getMessage());
    }
}
