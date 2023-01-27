package com.dku.springstudy.config.security.jwt;

import com.dku.springstudy.model.Role;
import lombok.Getter;

@Getter
public class Subject {
    private final String userId;
    private final String email;
    private final String nickname;
    private final String type;

    private final Role role;

    public Subject(String userId, String email, String nickname, String type, Role role) {
        this.userId = userId;
        this.email = email;
        this.nickname = nickname;
        this.type = type;
        this.role = role;
    }

    public static Subject atk(String userId, String email, String nickname, Role role) {
        return new Subject(userId, email, nickname, "ATK", Role.USER);
    }

    public static Subject rtk(String userId, String email, String nickname, Role role) {
        return new Subject(userId, email, nickname, "RTK", Role.USER);
    }
}
