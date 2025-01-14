package com.revature.shoply.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler(AddressNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleAddressNotFoundException(AddressNotFoundException e) {
        return createResponse(HttpStatus.NO_CONTENT, "Address Not Found", e.getMessage());
    }

    @ExceptionHandler(PaymentMethodNotFoundException.class)
    public ResponseEntity<Map<String, String>> handlePaymentMethodNotFoundException(PaymentMethodNotFoundException e) {
        return createResponse(HttpStatus.NO_CONTENT, "Payment Method Not Found", e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgument(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("Illegal Argument", e.getMessage()));
    }

    @ExceptionHandler(UniqueConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleUsernameAlreadyExistsException(UniqueConstraintViolationException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("Field's unique constraint violated", e.getMessage()));
    }

    private ResponseEntity<Map<String, String>> createResponse(HttpStatus status, String error, String message) {
        return ResponseEntity.status(status).body(Map.of("error", error, "message", message));
    }
}
