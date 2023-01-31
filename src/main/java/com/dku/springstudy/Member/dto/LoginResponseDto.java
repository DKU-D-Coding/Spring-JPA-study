package com.dku.springstudy.Member.dto;

import lombok.Data;

@Data
public class LoginResponseDto {
    private final String accessToken;
    private final Long tokenExpireTime;

    public LoginResponseDto(String accessToken, Long tokenExpireTime) {
        this.accessToken = accessToken;
        this.tokenExpireTime = tokenExpireTime;
    }
}
