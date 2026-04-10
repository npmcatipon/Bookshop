package com.training.project.exception;

public class DuplicateBookException extends RuntimeException {
    
    public DuplicateBookException() {
        super("Book already exists.");
    }

}
