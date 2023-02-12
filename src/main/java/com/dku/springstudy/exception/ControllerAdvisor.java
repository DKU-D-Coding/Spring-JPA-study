package com.dku.springstudy.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j

public class ControllerAdvisor {
    @ExceptionHandler(value = KarrotException.class)
    protected ResponseEntity exceptionHandler(KarrotException e){
        log.error("KarrotException : ", e);
        return ResponseEntity.status(e.getHttpStatus()).body(new KarrotException(e.getHttpStatus(), e.getCode(), e.getMessage()));
    }
}
