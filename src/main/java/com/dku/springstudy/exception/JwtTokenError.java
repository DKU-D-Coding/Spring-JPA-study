package com.dku.springstudy.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class JwtTokenError {
    private String message;
    private HttpStatus status;
}
