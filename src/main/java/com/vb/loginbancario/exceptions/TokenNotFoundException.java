package com.vb.loginbancario.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Token not found")
public class TokenNotFoundException extends RuntimeException{
    public TokenNotFoundException() {
        super("Token not found");
    }
}
