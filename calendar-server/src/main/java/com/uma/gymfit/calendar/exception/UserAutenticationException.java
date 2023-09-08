package com.uma.gymfit.calendar.exception;

public class UserAutenticationException extends RuntimeException {

    public UserAutenticationException(String message) {
        super(message);
    }

    public UserAutenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}
