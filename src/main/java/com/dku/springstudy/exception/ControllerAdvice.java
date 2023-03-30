package com.dku.springstudy.exception;

import com.dku.springstudy.dto.common.ErrorResponseDto;
import com.dku.springstudy.dto.common.ExceptionDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler(CustomException.class)
    public ExceptionDto exceptionHandler(CustomException e) {
        return new ExceptionDto(e);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class) // @Vaild exception 처리
    public ExceptionDto processValidationError(MethodArgumentNotValidException e) {
        return new ExceptionDto(e);
    }


}
