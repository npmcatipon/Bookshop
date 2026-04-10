package com.training.project.exception;

public class NoBooksAvailableException extends RuntimeException {

    public NoBooksAvailableException() {
        super("No Books Available.");
    }

}
