package com.vb.loginsystem.exceptions;

public class TokenExpiredException extends RuntimeException{
    public TokenExpiredException() {
        super("Token expired");
    }
}
