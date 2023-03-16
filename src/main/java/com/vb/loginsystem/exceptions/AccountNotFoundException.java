package com.vb.loginsystem.exceptions;

public class AccountNotFoundException extends RuntimeException{
    public AccountNotFoundException() {
        super("Account not found");
    }
}
