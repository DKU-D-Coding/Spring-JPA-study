package com.dku.springstudy.dto.user;

import lombok.Data;

@Data
public class SignUpRequestDTO {
    private final String email;
    private final String password;
    private final String username;
    private final String phoneNumber;
    private final String nickname;
}
