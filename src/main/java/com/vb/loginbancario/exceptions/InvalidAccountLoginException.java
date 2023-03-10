package com.vb.loginbancario.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Invalid account number or password")
public class InvalidAccountLoginException extends RuntimeException{
    public InvalidAccountLoginException() {
        super("Invalid account number or password");
    }
}
