package com.revature.shoply.reviews.exceptions;

public class NotAuthorizedException extends RuntimeException{

    public NotAuthorizedException() {
        super("Not Authorized");
    }

    public NotAuthorizedException(String message) {
        super(message);
    }

    public NotAuthorizedException(String message, Throwable cause) {
        super(message, cause);
    }

}
