package com.revature.shoply.orders.exceptions;

public class UnauthorizedUserActionException extends RuntimeException {

    public UnauthorizedUserActionException() {
        super("User is not authorized to perform this action");
    }

    public UnauthorizedUserActionException(String message) {
        super(message);
    }

    public UnauthorizedUserActionException(String message, Throwable cause) {
        super(message, cause);
    }

}
