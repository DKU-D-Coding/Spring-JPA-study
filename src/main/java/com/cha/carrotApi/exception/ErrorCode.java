package com.cha.carrotApi.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    USER_EMAIL_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 이메일을 가진 회원을 찾을 수 없습니다."),
    USER_EMAIL_DUPLICATED(HttpStatus.CONFLICT, "이미 가입된 이메일입니다."),
    USER_PASSWORD_INVALID(HttpStatus.UNAUTHORIZED, "비밀번호가 틀렸습니다."),
    USER_NICKNAME_DUPLICATED(HttpStatus.CONFLICT, "닉네임이 이미 존재합니다."),
    USER_PASSWORD_INSERT_ERROR(HttpStatus.UNAUTHORIZED, "비밀번호를 정확하게 입력해주세요"),
    USER_PHONE_NUMBER_DUPLICATED(HttpStatus.CONFLICT, "동일한 전화번호가 존재합니다."),
    DUPLICATE_RESOURCE(HttpStatus.CONFLICT, "데이터가 이미 존재합니다.")

    ;

    private final HttpStatus httpStatus;
    private final String message;

}
