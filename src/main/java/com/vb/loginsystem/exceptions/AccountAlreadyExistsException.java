package com.vb.loginsystem.exceptions;

public class AccountAlreadyExistsException extends RuntimeException{
    public AccountAlreadyExistsException() {
        super("Account already exists");
    }
}
