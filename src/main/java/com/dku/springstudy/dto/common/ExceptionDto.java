package com.dku.springstudy.dto.common;

import com.dku.springstudy.exception.CustomException;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;

@Getter
public class ExceptionDto {
    private final HttpStatus error;
    private final Integer status;
    private final String message;

    public ExceptionDto(CustomException e) {
        this.status = e.getErrorCode().getStatus();
        this.error = e.getErrorCode().getError();
        this.message = e.getErrorCode().getMessage();
    }

    public ExceptionDto(MethodArgumentNotValidException e){ // @Vaild exception
        this.error = (HttpStatus) e.getStatusCode();
        this.status = this.error.value();
        this.message = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
    }

}
