package com.revature.shoply.user.exception;

public class PaymentMethodNotFoundException extends RuntimeException {

    public PaymentMethodNotFoundException() {
        super("Payment method not found");
    }

    public PaymentMethodNotFoundException(String message) {
        super(message);
    }

    public PaymentMethodNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
