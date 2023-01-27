package com.dku.springstudy.config.security.jwt;

import lombok.Getter;

@Getter
public class Subject {
    private final String userId;
    private final String email;
    private final String nickname;
    private final String type;

    public Subject(String userId, String email, String nickname, String type) {
        this.userId = userId;
        this.email = email;
        this.nickname = nickname;
        this.type = type;
    }

    public static Subject atk(String userId, String email, String nickname) {
        return new Subject(userId, email, nickname, "ATK");
    }

    public static Subject rtk(String userId, String email, String nickname) {
        return new Subject(userId, email, nickname, "RTK");
    }
}
