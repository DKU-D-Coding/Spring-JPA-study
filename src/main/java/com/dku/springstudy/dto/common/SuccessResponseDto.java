package com.dku.springstudy.dto.common;

import lombok.Getter;

@Getter
public class SuccessResponseDto<T> extends ResponseDto{
    private final T data;

    public SuccessResponseDto(T data) {
        super(true);
        this.data = data;
    }
}
