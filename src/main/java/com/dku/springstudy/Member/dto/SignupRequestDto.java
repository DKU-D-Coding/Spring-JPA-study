package com.dku.springstudy.Member.dto;

import com.dku.springstudy.Member.entity.MemberROLE;
import lombok.Data;

@Data
public class SignupRequestDto {
    private String email;

    private String password;

    private String name;

    private String nickname;

    private MemberROLE memberRole;

    private String phoneNumber;
}
