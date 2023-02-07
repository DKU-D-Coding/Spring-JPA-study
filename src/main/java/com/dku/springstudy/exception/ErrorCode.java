package com.dku.springstudy.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    USER_NOT_FOUND_ERROR(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(), "User not found"),
    USER_ALREADY_EXIST_ERROR(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(), "User already exist"),
    USER_PASSWORD_INCORRECT_ERROR(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(), "User password incorrect"),
    EXPIRED_TOKEN_ERROR(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(), "Expired JWT token"),
    INVALID_TOKEN_ERROR(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(), "Invalid JWT token")
    ;
    /**
     * 추가
     */
    private final HttpStatus error;
    private final Integer status;
    private final String message;
}
