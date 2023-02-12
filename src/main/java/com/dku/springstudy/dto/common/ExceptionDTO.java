package com.dku.springstudy.dto.common;

import com.dku.springstudy.exception.CustomException;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;

@Getter
public class ExceptionDTO {
    private final HttpStatus error;
    private final Integer status;
    private final String message;

    public ExceptionDTO(CustomException e) {
        this.status = e.getErrorCode().getStatus();
        this.error = e.getErrorCode().getError();
        this.message = e.getErrorCode().getMessage();
    }

    public ExceptionDTO(MethodArgumentNotValidException e) {
        this.error = (HttpStatus) e.getStatusCode();
        this.status = this.error.value();
        this.message = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
    }
}
