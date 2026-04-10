package com.training.project.exception;

public class ApiResponse {

    private int status;
    private String message;

    public ApiResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public static ApiResponse of(int status, String message) {
        return new ApiResponse(status, message);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
