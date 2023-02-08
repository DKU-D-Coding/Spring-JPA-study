package com.dku.springstudy.member.dto;

import com.dku.springstudy.member.entity.MemberROLE;
import lombok.Data;

@Data
public class SignupRequestDto {
    private String email;

    private String password;

    private String name;

    private String nickname;

    private String phoneNumber;
}
