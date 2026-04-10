package com.training.project.exception;

public class BookIdNotFoundException extends RuntimeException {

    public BookIdNotFoundException() {
        super("Book ID not found.");
    }
}
