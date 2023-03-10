package com.vb.loginbancario.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Email already confirmed")
public class EmailAlreadyConfirmedException extends RuntimeException{
    public EmailAlreadyConfirmedException() {
        super("Email already confirmed");
    }
}
