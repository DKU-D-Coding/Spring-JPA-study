package com.dku.springstudy.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JoinDto {
    @ApiModelProperty(value = "이메일", dataType = "string")
    private String email;
    @ApiModelProperty(value = "비밀번호", dataType = "string")
    private String password;
    @ApiModelProperty(value = "이름", dataType = "string")
    private String name;
    @ApiModelProperty(value = "전화번호", dataType = "string")
    private String phone;
    @ApiModelProperty(value = "닉네임", dataType = "string")
    private String nickname;

}
