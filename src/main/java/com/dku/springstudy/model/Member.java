package com.dku.springstudy.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Member {
    @Id
    private String email;
    private String password;
    private String name;
    private String phone;
    private String nickname;

    @Enumerated(EnumType.STRING)
    private Role role;
}
