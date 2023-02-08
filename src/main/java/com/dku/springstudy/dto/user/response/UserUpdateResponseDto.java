package com.dku.springstudy.dto.user.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class UserUpdateResponseDto {

    @Schema(example = "https://d-coding.s3.ap-northeast-2.amazonaws.com/67a29351-9339-42b0-ba46-da6584ab93cc.jpg", description = "업로드한 사진 url")
    private String imgUrl;

    @Schema(example = "sujjeong", description = "닉네임")
    @NotBlank
    private String nickname;

    public static UserUpdateResponseDto of(String imgUrl, String nickname) {
        return new UserUpdateResponseDto(imgUrl, nickname);
    }
}
