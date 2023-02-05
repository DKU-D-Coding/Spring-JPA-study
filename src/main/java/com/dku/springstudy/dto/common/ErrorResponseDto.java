package com.dku.springstudy.dto.common;

import com.dku.springstudy.exception.CustomException;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;

@Getter
public class ErrorResponseDto extends ResponseDto {
    private final HttpStatus status;
    private final String code;
    private final String message;

    public ErrorResponseDto(CustomException e) {
        super(false);
        this.status = e.getErrorCode().getStatus();
        this.code = e.getErrorCode().name();
        this.message = e.getErrorCode().getMessage();
    }

    public ErrorResponseDto(MethodArgumentNotValidException e){ // @Vaild exception
        super(false);
        this.code = e.getBindingResult().getAllErrors().get(0).getCode();
        this.status = (HttpStatus) e.getStatusCode();
        this.message = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
    }

}
