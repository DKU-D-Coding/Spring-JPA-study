package com.dku.springstudy.dto.member.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponseDto {
    private String accessToken;
    private String refreshToken;

    public LoginResponseDto LoginResponse(String accessToken, String refreshToken) {
        return new LoginResponseDto(accessToken,refreshToken);
    }

}
