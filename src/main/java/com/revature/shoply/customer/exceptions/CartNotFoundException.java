package com.revature.shoply.customer.exceptions;

public class CartNotFoundException extends RuntimeException{

    public CartNotFoundException() {
        super("Cart not found");
    }

    public CartNotFoundException(String message) {
        super(message);
    }

    public CartNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
