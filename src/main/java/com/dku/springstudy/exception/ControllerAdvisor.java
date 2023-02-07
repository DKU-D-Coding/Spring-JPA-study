package com.dku.springstudy.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice

public class ControllerAdvisor {
    @ExceptionHandler(value = KarrotException.class)
    protected ResponseEntity exceptionHandler(KarrotException e){
        return ResponseEntity.status(e.getHttpStatus()).body(new KarrotException(e.getHttpStatus(), e.getCode(), e.getMessage()));
    }
}
