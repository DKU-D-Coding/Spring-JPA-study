package com.dku.springstudy.dto.user.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class SignUpResponseDto {

    @Schema(example = "12", description = "회원가입이 완료된 사용자의 PK")
    private Long id;

    public static SignUpResponseDto of(Long id) {
        return new SignUpResponseDto(id);
    }
}
