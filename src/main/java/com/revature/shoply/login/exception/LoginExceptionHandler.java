package com.revature.shoply.login.exception;

import org.apache.http.auth.AuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global exception handler for login-related exceptions.
 *
 * This class uses the {@link RestControllerAdvice} annotation to provide centralized exception handling
 * for REST controllers. It specifically handles exceptions of type {@link IllegalArgumentException}
 * and maps them to an appropriate HTTP response.
 */
@RestControllerAdvice
public class LoginExceptionHandler {

    /**
     * Handles {@link IllegalArgumentException} exceptions.
     *
     * This method intercepts {@link IllegalArgumentException} thrown by the application,
     * typically during login attempts, and returns an HTTP 401 (Unauthorized) response with the exception message.
     *
     * @param e The {@link IllegalArgumentException} to handle.
     * @return A {@link ResponseEntity} containing the exception message as the response body and an HTTP 401 status.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

}
