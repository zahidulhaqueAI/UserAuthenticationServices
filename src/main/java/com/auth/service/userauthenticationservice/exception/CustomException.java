package com.auth.service.userauthenticationservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomException {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> runtimeException(RuntimeException e) {
        String msg = e.getMessage();
        return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
    }

//    @ExceptionHandler(IllegalArgumentException.class)
//    public ResponseEntity<String> illegalArgumentException(IllegalArgumentException e) {
//        String msg = e.getMessage();
//        return new ResponseEntity<>(msg, HttpStatus.NETWORK_AUTHENTICATION_REQUIRED);
//    }
}
