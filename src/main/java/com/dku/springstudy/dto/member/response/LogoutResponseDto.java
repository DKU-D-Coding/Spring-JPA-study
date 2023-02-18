package com.dku.springstudy.dto.member.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LogoutResponseDto {
    private boolean success;
    private String message = "";

    public LogoutResponseDto(boolean success){
        this.success = success;
    }

    public LogoutResponseDto(boolean success, String message){
        this.success = success;
        this.message = message;
    }
}
