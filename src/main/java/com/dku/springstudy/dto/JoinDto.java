package com.dku.springstudy.dto;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JoinDto {
    private String email;
    private String password;
    private String name;
    private String phone;
    private String nickname;

}
