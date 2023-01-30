package com.dku.springstudy.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@NoArgsConstructor @AllArgsConstructor
@Getter @Builder
@Table(name = "Users", uniqueConstraints = {@UniqueConstraint(columnNames = "email")})
@Entity
public class User extends BaseTimeEntity{

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid",strategy = "uuid")
    @Column(name = "user_id")
    private String id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String profileImageUrl;

    @Setter
    @Lob
    private String token;

    @Column(nullable = false)
    private String phoneNumber;

    private String nickname;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    public void changeProfileImageAndNickname(String url, String nickname){
        this.profileImageUrl = url;
        this.nickname = nickname;
    }
}
