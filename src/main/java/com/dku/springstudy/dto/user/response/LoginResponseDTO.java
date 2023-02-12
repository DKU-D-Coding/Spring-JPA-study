package com.dku.springstudy.dto.user.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponseDTO {
    private String accessToken;
    private String refreshToken;

    public LoginResponseDTO LoginResponse(String accessToken, String refreshToken) {
        return new LoginResponseDTO(accessToken,refreshToken);
    }
}
