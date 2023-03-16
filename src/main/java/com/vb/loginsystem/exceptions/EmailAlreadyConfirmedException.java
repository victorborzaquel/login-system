package com.vb.loginsystem.exceptions;

public class EmailAlreadyConfirmedException extends RuntimeException{
    public EmailAlreadyConfirmedException() {
        super("Email already confirmed");
    }
}
