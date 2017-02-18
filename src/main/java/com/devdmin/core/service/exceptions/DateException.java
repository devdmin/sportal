package com.devdmin.core.service.exceptions;


public class DateException extends RuntimeException  {
    public DateException(String message, Throwable cause) {
        super(message, cause);
    }

    public DateException(String message) {
        super(message);
    }

    public DateException() {
        super();
    }
}
