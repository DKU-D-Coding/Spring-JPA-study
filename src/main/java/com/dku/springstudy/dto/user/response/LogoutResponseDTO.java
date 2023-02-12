package com.dku.springstudy.dto.user.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LogoutResponseDTO {
    private boolean success;
    private String message = "";

    public LogoutResponseDTO(boolean success){
        this.success = success;
    }

    public LogoutResponseDTO(boolean success, String message){
        this.success = success;
        this.message = message;
    }
}
