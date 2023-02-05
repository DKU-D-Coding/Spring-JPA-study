package com.dku.springstudy.exception;

import com.dku.springstudy.dto.common.ErrorResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ErrorResponseDto> exceptionHandler(CustomException e) {
        return ResponseEntity
                .status(e.errorCode.getStatus())
                .body(new ErrorResponseDto(e));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class) // @Vaild exception 처리
    public Object processValidationError(MethodArgumentNotValidException e) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(e);

        return ResponseEntity
                .status(errorResponseDto.getStatus())
                .body(errorResponseDto);
    }


}
