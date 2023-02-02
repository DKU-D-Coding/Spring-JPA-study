package com.dku.springstudy.exception;

import com.dku.springstudy.dto.ErrorResponseResult;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler({
            UserNotFoundException.class
    })

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorResponseResult exceptionHandler(Exception e){
        return new ErrorResponseResult(e);
    }


    // @Valid, @ModelAttribute exception 처리 (Bean Validation 예외)
    /*
    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    protected ErrorResponseResult handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        String errorMessage = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return new ErrorResponseResult(errorMessage);
    }
    */

}
