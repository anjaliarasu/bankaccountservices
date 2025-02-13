package com.marlow.accountservice.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<?> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException exception) {

        // Map to store field errors and their corresponding error messages
        Map<String, String> errors = new HashMap<>();

        // Iterate through all validation errors
        exception.getBindingResult().getAllErrors().forEach(error -> {
            // Extract the field name and error message
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            // Add the field error to the map
            errors.put(fieldName, errorMessage);
        });

        // Return a response with status code 400 (Bad Request) and the error details
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globleExcpetionHandler(Exception ex, WebRequest request)
    {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    // Exception handler method for handling MethodArgumentNotValidException
    // Thrown when the required critera for name and email in user DTO
    // is not presented in the request recieved from frontend/postman API

}
