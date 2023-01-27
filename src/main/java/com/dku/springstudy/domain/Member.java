package com.dku.springstudy.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String email;
    private String password;
    private String username;
    private String phone;
    private String nickname;

    public void createMember(String email, String password, String username, String phone, String nickname){
        this.email = email;
        this.password = password;
        this.username = username;
        this.phone = phone;
        this.nickname = nickname;
    }

}
