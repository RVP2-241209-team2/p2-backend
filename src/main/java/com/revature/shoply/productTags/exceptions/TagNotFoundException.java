package com.revature.shoply.productTags.exceptions;

public class TagNotFoundException extends RuntimeException {

    public TagNotFoundException() {
        super("Tag not found");
    }

    public TagNotFoundException(String message) {
        super(message);
    }

    public TagNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
