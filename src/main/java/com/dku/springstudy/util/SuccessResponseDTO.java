package com.dku.springstudy.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.lang.Nullable;

@AllArgsConstructor
@Data
public class SuccessResponseDTO<T> {
    private final boolean success;
    private final T data;

    public SuccessResponseDTO(@Nullable T data) {
        this.success = true;
        this.data = data;
    }
}
