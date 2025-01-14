package com.revature.shoply.user.exception;

public class UniqueConstraintViolationException extends RuntimeException {

    public UniqueConstraintViolationException() {
        super("Fields unique constraint violated, value already exists in database");
    }

    public UniqueConstraintViolationException(String message) {
        super(message);
    }

    public UniqueConstraintViolationException(String message, Throwable cause) {
        super(message, cause);
    }
}
