package com.project.carrot.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.lang.Nullable;

@AllArgsConstructor
@Getter
public class SuccessResponseDTO<T> { //응답값을 통일하기 위한 DTO 생성
    private final Boolean success;
    private final T data;

    public SuccessResponseDTO(@Nullable T data){
        this.success=true;
        this.data=data;
    }
}

//다른 곳에서는 resultCode까지 반환했음