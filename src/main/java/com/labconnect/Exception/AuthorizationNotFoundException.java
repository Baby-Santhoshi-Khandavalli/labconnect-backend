package com.labconnect.Exception;

public class AuthorizationNotFoundException extends RuntimeException {
    public AuthorizationNotFoundException(String message) {
        super(message);
    }
}
