package com.revature.shoply.user.exception;

public class AddressNotFoundException extends RuntimeException {

        public AddressNotFoundException() {
            super("Address not found");
        }

        public AddressNotFoundException(String message) {
            super(message);
        }

        public AddressNotFoundException(String message, Throwable cause) {
            super(message, cause);
        }
}
