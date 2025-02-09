package com.example.testeltex.exception;

import com.example.testeltex.annotation.UserExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(annotations = UserExceptionHandler.class)
public class UsersAdvice {

    @ExceptionHandler(UsersNotFoundException.class)
    public ResponseEntity<String> handleUsersNotFoundException(UsersNotFoundException ex) {
        String errorMessage = "{\"message\": \"" + ex.getMessage() + "\"}";
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorMessage);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body("Server error: " + ex.getMessage());
    }
}
