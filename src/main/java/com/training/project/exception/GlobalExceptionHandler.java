package com.training.project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateBookException.class)
    public ResponseEntity<ApiResponse> handleDuplicateBook(
        Exception ex,
        HttpServletRequest req) {
            return ResponseEntity.status(
                HttpStatus.CONFLICT.value())
                .body(
                    ApiResponse.of(HttpStatus.CONFLICT.value(), 
                    ex.getMessage()));
    }

    @ExceptionHandler(BookIdNotFoundException.class)
    public ResponseEntity<ApiResponse> handleBookIdNotFound(
        Exception ex,
        HttpServletRequest req) {
            return ResponseEntity.status(
                HttpStatus.NOT_FOUND.value())
                .body(
                    ApiResponse.of(HttpStatus.NOT_FOUND.value(), 
                    ex.getMessage()));
    }
}
