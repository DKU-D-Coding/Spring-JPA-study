package com.dku.springstudy.dto.like;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class LikeClickResponseDto {

    @Schema(example = "add", description = "좋아요 추가 / 취소 여부")
    private String activity;

    public static LikeClickResponseDto of(String activity) {
        return new LikeClickResponseDto(activity);
    }
}
