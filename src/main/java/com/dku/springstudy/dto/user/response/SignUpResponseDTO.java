package com.dku.springstudy.dto.user.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignUpResponseDTO {
    private String accessToken;
    private String refreshToken;

    public SignUpResponseDTO LoginResponse(String accessToken, String refreshToken) {
        return new SignUpResponseDTO(accessToken,refreshToken);
    }

}
