package com.revature.shoply.product.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * This exception controller handles all exceptions related to Product management
 */
@RestControllerAdvice(basePackages = "com.revature.shoply.product") // defines REST global exception handler
public class ExceptionController {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        return "Invalid product details: missing properties";
    }

    @ExceptionHandler(ProductRegistrationException.class) // exception class to handle
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleProductRegistrationException(ProductRegistrationException e) {
        return e.getMessage();
    }

    @ExceptionHandler(ProductRepositoryException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleProductRepositoryException(ProductRepositoryException e) {
        return e.getMessage();
    }

}
