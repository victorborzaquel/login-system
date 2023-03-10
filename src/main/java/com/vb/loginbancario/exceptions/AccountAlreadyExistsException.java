package com.vb.loginbancario.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Account already exists")
public class AccountAlreadyExistsException extends RuntimeException{
    public AccountAlreadyExistsException() {
        super("Account already exists");
    }
}
