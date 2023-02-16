package com.dku.springstudy.domain.user.dto;

import lombok.Data;

@Data
public class UserProfileUpdateRequestDTO {
    private String nickname;
    private String profileImgUrl;

}
