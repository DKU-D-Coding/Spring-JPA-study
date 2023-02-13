package com.project.carrot.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    CUSTOM_ERROR(HttpStatus.BAD_REQUEST,"잘못된 요청입니다 :)");

    private final HttpStatus status;
    private final String message;
}
