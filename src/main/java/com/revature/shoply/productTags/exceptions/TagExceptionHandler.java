package com.revature.shoply.productTags.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class TagExceptionHandler {

    @ExceptionHandler(TagNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleTagNotFoundException(TagNotFoundException e) {
        return createResponse(HttpStatus.NOT_FOUND, "Tag Not Found", e.getMessage());
    }

    private ResponseEntity<Map<String, String>> createResponse(HttpStatus status, String error, String message) {
        return ResponseEntity.status(status).body(Map.of("error", error, "message", message));
    }

}
