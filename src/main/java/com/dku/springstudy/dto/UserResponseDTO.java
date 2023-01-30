package com.dku.springstudy.dto;

import com.dku.springstudy.model.Role;
import com.dku.springstudy.model.User;
import lombok.Data;

@Data
public class UserResponseDTO {
    private final String userId;

    private final String email;

    private final String nickname;
    private Role role = Role.USER;

    public UserResponseDTO(String userId, String email, String nickname, Role role) {
        this.userId = userId;
        this.email = email;
        this.nickname = nickname;
        this.role = role;
    }

    public static UserResponseDTO of(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getEmail(),
                user.getNickname(),
                user.getRole());
    }
}
