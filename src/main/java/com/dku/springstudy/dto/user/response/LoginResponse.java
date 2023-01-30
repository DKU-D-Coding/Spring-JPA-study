package com.dku.springstudy.dto.user.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class LoginResponse {

    private String loginAccessToken;
    private String loginRefreshToken;

    public static LoginResponse of(String loginAccessToken, String loginRefreshToken) {
        return new LoginResponse(loginAccessToken, loginRefreshToken);
    }
}
