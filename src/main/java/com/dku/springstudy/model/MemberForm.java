package com.dku.springstudy.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberForm {
    private String email;
    private String password;
    private String name;
    private String phone;
    private String nickname;
}
