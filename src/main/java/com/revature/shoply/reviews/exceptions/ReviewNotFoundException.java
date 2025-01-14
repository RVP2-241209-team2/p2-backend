package com.revature.shoply.reviews.exceptions;

public class ReviewNotFoundException extends RuntimeException{

    public ReviewNotFoundException() {
        super("Review not found");
    }

    public ReviewNotFoundException(String message) {
        super(message);
    }

    public ReviewNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
