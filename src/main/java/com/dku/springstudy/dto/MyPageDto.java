package com.dku.springstudy.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MyPageDto {
    @ApiModelProperty(value = "프로필 이미지 경로", dataType = "string")
    private String profileImagePath;
    @ApiModelProperty(value = "닉네임", dataType = "string")
    private String nickname;

}
