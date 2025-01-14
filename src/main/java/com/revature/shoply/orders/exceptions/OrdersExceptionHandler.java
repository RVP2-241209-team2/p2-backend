package com.revature.shoply.orders.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class OrdersExceptionHandler {

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleOrderNotFoundException(OrderNotFoundException e) {
        return createResponse(HttpStatus.NO_CONTENT, "Order Not Found", e.getMessage());
    }

    @ExceptionHandler(UnauthorizedUserActionException.class)
    public ResponseEntity<Map<String, String>> handleUnauthorizedUserActionException(UnauthorizedUserActionException e) {
        return createResponse(HttpStatus.FORBIDDEN, "Unauthorized User Action", e.getMessage());
    }

    private ResponseEntity<Map<String, String>> createResponse(HttpStatus status, String error, String message) {
        return ResponseEntity.status(status).body(Map.of("error", error, "message", message));
    }

}
