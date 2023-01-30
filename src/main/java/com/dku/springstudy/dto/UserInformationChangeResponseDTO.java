package com.dku.springstudy.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserInformationChangeResponseDTO {
    private String imageUrl;
    private String nickname;
}
