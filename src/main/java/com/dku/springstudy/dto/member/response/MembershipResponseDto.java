package com.dku.springstudy.dto.member.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MembershipResponseDto {
    private String accessToken;
    private String refreshToken;

    public MembershipResponseDto LoginResponse(String accessToken, String refreshToken) {
        return new MembershipResponseDto(accessToken,refreshToken);
    }

}
