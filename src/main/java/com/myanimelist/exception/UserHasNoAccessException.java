package com.myanimelist.exception;

public class UserHasNoAccessException extends RuntimeException {

    public UserHasNoAccessException(String message) {
        super(message);
    }
}
