package com.dku.springstudy.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupDTO {
    private String email;
    private String password;
    private String name;
    private String phone;
    private String nickname;
}
