package com.dku.springstudy.dto;

import lombok.Data;

@Data
public class SignUpRequest {
    private final String email;
    private final String password;
    private final String username;
    private final String phoneNumber;
    private final String nickname;
}
