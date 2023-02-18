package com.dku.springstudy.dto.category.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CategoryDeleteResponseDto {

    private boolean success;
    private String message = "";

    public CategoryDeleteResponseDto(boolean success){
        this.success = success;
    }

    public CategoryDeleteResponseDto(boolean success, String message){
        this.success = success;
        this.message = message;
    }

}
