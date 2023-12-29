package com.assignment.travelagencymanagement.exception;

public class CustomException extends RuntimeException{
    private static final long serialVersionUID = 106976543L;
    public CustomException(String message, Throwable cause) {
        super(message, cause);
    }
    public CustomException(String message) {
        super(message);
    }
    public CustomException(Throwable cause) {
        super(cause);
    }

}