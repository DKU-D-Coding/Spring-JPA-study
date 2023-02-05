package com.dku.springstudy.domain.user.dto;

import com.dku.springstudy.domain.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class SignUpRequestDTO {
    @NonNull
    private String email;
    @NonNull
    private String password;
    @NonNull
    private String username;
    @NonNull
    private String phoneNumber;
    @NonNull
    private String nickname;

    public User toEntity() {
        return User.builder()
                .email(email)
                .password(password)
                .username(username)
                .phoneNumber(phoneNumber)
                .nickname(nickname)
                .build();
    }
}
