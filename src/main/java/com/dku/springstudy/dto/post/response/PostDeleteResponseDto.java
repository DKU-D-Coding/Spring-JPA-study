package com.dku.springstudy.dto.post.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PostDeleteResponseDto {

    private boolean success;
    private String message = "";

    public PostDeleteResponseDto(boolean success){
        this.success = success;
    }

    public PostDeleteResponseDto(boolean success, String message){
        this.success = success;
        this.message = message;
    }

}
