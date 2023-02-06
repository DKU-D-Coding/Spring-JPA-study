package com.dku.springstudy.dto.member.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponseDto {
    private String loginAccessToken;
    private String loginRefreshToken;

    public LoginResponseDto LoginResponse(String loginAccessToken, String loginRefreshToken) {
        return new LoginResponseDto(loginAccessToken,loginRefreshToken);
    }

    /*
    public LoginResponseDto LoginResponse(String loginAccessToken) {
        return new LoginResponseDto(loginAccessToken);
    }

     */

}
