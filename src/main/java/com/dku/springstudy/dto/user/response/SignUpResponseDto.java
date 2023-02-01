package com.dku.springstudy.dto.user.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class SignUpResponseDto {

    private Long id;

    public static SignUpResponseDto of(Long id) {
        return new SignUpResponseDto(id);
    }
}
