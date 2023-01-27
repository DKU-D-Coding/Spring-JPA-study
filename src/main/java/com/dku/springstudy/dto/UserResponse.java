package com.dku.springstudy.dto;

import com.dku.springstudy.model.User;
import lombok.Data;

@Data
public class UserResponse {
    private final String userId;

    private final String email;

    private final String nickname;

    public UserResponse(String userId, String email, String nickname) {
        this.userId = userId;
        this.email = email;
        this.nickname = nickname;
    }

    public static UserResponse of(User user) {
        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getNickname());
    }
}
