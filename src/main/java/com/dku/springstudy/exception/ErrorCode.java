package com.dku.springstudy.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    USER_NOT_FOUND_ERROR(HttpStatus.BAD_REQUEST, "User not found"),
    USER_ALREADY_EXIST_ERROR(HttpStatus.BAD_REQUEST, "User already exist"),
    USER_PASSWORD_INCORRECT_ERROR(HttpStatus.BAD_REQUEST, "User password incorrect");
    /**
     * 추가
     */
    private final HttpStatus status;
    private final String message;
}
