package com.dku.springstudy.dto.member;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MemberDto {
    private Long id;
    private String email;
    private String password;
    private String name;
}
