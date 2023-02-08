package com.dku.springstudy.member.entity;

public enum MemberROLE {
    //Config에서 검사 시 ROLE + config에 등록한 이름으로 검사함...
    ROLE_USER("USER"),
    ROLE_ADMIN("ADMIN"),
    ;

    final String role;

    MemberROLE(String role) {
        this.role = role;
    }

    public String role() { return role; }
}
