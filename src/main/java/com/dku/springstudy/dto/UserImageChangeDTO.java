package com.dku.springstudy.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserImageChangeDTO {
    private String imageUrl;
    private String nickname;
}
