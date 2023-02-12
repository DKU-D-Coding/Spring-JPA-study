package com.dku.springstudy.dto.auth.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class LoginResponseDto {

    @Schema(description = "로그인 성공 시 발급되는 토큰")
    private String accessToken;
    @Schema(description = "로그인 성공 시 발급되는 재발급토큰")
    private String refreshToken;

    public static LoginResponseDto of(String accessToken, String refreshToken) {
        return new LoginResponseDto(accessToken, refreshToken);
    }
}
