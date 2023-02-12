package com.dku.springstudy.dto.common;

import lombok.Getter;

@Getter
public class ErrorResponseDTO<T> extends ResponseDTO{
    private final T data;

    public ErrorResponseDTO(T data) {
        super(false);
        this.data = data;
    }
}
