package com.assignment.travelagencymanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;
import java.sql.SQLException;

@ControllerAdvice
public class CustomErrorAdvice {

    @ExceptionHandler({ CustomException.class, SQLException.class, NullPointerException.class, IOException.class})
    public ResponseEntity<ErrorInfo> handle(Exception ce) {
        ErrorInfo error = new ErrorInfo(ce.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

}
