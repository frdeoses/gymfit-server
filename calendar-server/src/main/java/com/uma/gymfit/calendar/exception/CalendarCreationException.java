package com.uma.gymfit.calendar.exception;

public class CalendarCreationException extends RuntimeException {

    public CalendarCreationException(String message) {
        super(message);
    }

    public CalendarCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
