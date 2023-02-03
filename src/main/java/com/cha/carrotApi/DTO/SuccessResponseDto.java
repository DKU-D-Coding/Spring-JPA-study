package com.cha.carrotApi.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.lang.Nullable;

@AllArgsConstructor
@Getter
public class SuccessResponseDto<T> {
    private final Boolean success;
    private final T data;

    public SuccessResponseDto(@Nullable T data) {
        this.success = true;
        this.data = data;
    }
}
