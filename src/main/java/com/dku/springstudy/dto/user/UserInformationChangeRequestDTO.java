package com.dku.springstudy.dto.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserInformationChangeRequestDTO {
    private String imageUrl;
    private String nickname;
}
