package com.training.project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateBookException.class)
    public ResponseEntity<ApiResponse<Void>> handleDuplicateBook(
        Exception ex) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(ApiResponse.error(
                                        HttpStatus.CONFLICT.value(), 
                                        ex.getMessage()));
    }

    @ExceptionHandler(BookIdNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleBookIdNotFound(
        Exception ex) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(
                                        HttpStatus.NOT_FOUND.value(), 
                                        ex.getMessage()
                    ));
    }

    @ExceptionHandler(NoBooksAvailableException.class)
        public ResponseEntity<ApiResponse<Void>> handleNoBooksAvailable(
            Exception ex) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error(
                                        HttpStatus.NOT_FOUND.value(), 
                                        ex.getMessage()));
    }
}