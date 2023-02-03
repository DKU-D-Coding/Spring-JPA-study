package com.dku.springstudy.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.http.HttpException;
import org.springframework.http.HttpStatus;


@AllArgsConstructor
@Getter
public enum ErrorCode {
    USER_EMAIL_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 이메일을 가진 회원을 찾을 수 없습니다."),
    USER_EMAIL_Duplication(HttpStatus.CONFLICT, "이미 가입된 이메일입니다."),
    USER_PASSWORD_NOT_MATCHES(HttpStatus.UNAUTHORIZED, "비밀번호가 틀렸습니다."),
    FILE_EXTENSION_NOT_SUPPORT(HttpStatus.BAD_REQUEST, "지원하지 않는 파일 확장자입니다."),
    FILE_UPLOAD_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드에 실패했습니다."),
    ;

    private final HttpStatus httpstatus;
    private final String message;

    ErrorCode(HttpException e) {
        this.httpstatus = HttpStatus.INTERNAL_SERVER_ERROR;
        this.message = e.getMessage();
    }
}
