package com.dku.springstudy.dto.common;

import lombok.Getter;

@Getter
public class SuccessResponse<T> extends BaseResponse {

    private T data;

    public SuccessResponse(T data) {
        super(true);
        this.data = data;
    }
}
