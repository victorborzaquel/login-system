package com.vb.loginsystem.exceptions;

public class InvalidAccountLoginException extends RuntimeException{
    public InvalidAccountLoginException() {
        super("Invalid account number or password");
    }
}
