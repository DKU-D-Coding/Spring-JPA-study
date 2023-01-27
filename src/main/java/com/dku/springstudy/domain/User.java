package com.dku.springstudy.domain;

import com.dku.springstudy.domain.common.BaseTimeEntity;
import com.dku.springstudy.domain.constant.Role;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String phoneNumber;

    @Column(unique = true, nullable = false)
    private String nickname;

    @Column(nullable = false)
    private int status;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    private User(String email, String password, String name, String phoneNumber, String nickname) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.nickname = nickname;
        this.status = 1;
        this.role = role.USER;
    }
}
