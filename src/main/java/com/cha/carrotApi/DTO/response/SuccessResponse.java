package com.cha.carrotApi.DTO.response;

import lombok.Getter;
import org.springframework.lang.Nullable;

@Getter
public class SuccessResponse<T> extends BaseResponse{

    private T data;

    public SuccessResponse(@Nullable T data) {
        super(true);
        this.data = data;
    }
}
