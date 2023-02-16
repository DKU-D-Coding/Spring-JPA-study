package com.dku.springstudy.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class TokenDto {
    //grantType은 JWT 대한 인증 타입
    private String grantType;
    private String accessToken;
    private String refreshToken;
}
