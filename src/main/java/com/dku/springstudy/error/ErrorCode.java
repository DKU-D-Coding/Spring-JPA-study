package com.dku.springstudy.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Getter
public enum ErrorCode {
    ALREADY_EXIST(400, "G001", "이미 존재합니다"),
    NOT_EXIST(400, "G002", "존재하지 않습니다"),
    TOKEN_VALIDATE_FAILED(400, "A001", "인증 토큰이 잘못되었습니다");

    private final int httpStatus;
    private final String code;
    private final String message;
    ErrorCode(final int httpStatus, final String code, final String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}
