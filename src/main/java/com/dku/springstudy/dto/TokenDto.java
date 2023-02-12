package com.dku.springstudy.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class TokenDto {

    @ApiModelProperty(value = "토큰 타입(Bearer사용)", dataType = "string")
    private String grantType; // Bearer

    @ApiModelProperty(value = "Access Token", dataType = "string")
    private String accessToken;

    @ApiModelProperty(value = "Refresh Token", dataType = "string")
    private String refreshToken;

    public TokenDto(){
    }

}
