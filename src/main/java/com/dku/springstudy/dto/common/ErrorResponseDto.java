package com.dku.springstudy.dto.common;

import lombok.Getter;

@Getter
public class ErrorResponseDto<T> extends ResponseDto{
    private final T data;

    public ErrorResponseDto(T data) {
        super(false);
        this.data = data;
    }
}
