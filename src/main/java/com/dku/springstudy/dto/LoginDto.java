package com.dku.springstudy.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {
    @ApiModelProperty(value = "이메일", dataType = "string")
    private String email;
    @ApiModelProperty(value = "비밀번호", dataType = "string")
    private String password;



}
