package com.dku.springstudy.Member.entity;

public enum MemberROLE {
    USER("USER"),
    ADMIN("ADMIN"),
    ;

    final String role;

    MemberROLE(String role) {
        this.role = role;
    }

    public String role() { return role; }
}
