package com.dku.springstudy.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Data
public class KarrotException extends RuntimeException{
    private final HttpStatus httpStatus;
    private final int code;
    private final String message;

    public KarrotException(HttpStatus httpStatus, int code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}
