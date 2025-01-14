package com.revature.shoply.customer.exceptions;

public class ItemNotFoundException extends RuntimeException{

    public ItemNotFoundException() {
        super("Item not found");
    }

    public ItemNotFoundException(String message) {
        super(message);
    }

    public ItemNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
