package com.dku.springstudy.exception;

import com.dku.springstudy.dto.common.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ErrorResponse> customExceptionHandle(CustomException e) {
        log.error("ExceptionHandler throw CustomException : {}", e.getErrorCode());

        HttpStatus status = e.getErrorCode().getHttpstatus();

        return ResponseEntity
                .status(status)
                .body(new ErrorResponse(status.value(), e.getErrorCode().getMessage()));
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> exceptionHandle(Exception e){
        log.error("ExceptionHandler throw Exception : {}", e.getMessage());

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        return ResponseEntity
                .status(status)
                .body(new ErrorResponse(status.value(), e.getMessage()));
    }
}
