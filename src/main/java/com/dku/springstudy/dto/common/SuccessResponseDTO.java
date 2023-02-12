package com.dku.springstudy.dto.common;

import lombok.Getter;

@Getter
public class SuccessResponseDTO<T> extends ResponseDTO{
    private final T data;

    public SuccessResponseDTO(T data) {
        super(true);
        this.data = data;
    }
}
