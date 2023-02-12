package com.dku.springstudy.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateProfilesDto {
    @ApiModelProperty(value = "수정할 회원 닉네임", dataType = "string")
    private String nickname;
}
