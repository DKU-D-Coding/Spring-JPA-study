package com.dku.springstudy.domain.user;

import com.dku.springstudy.domain.BaseTimeEntity;
import com.dku.springstudy.domain.like.Like;
import com.dku.springstudy.domain.product.Product;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "user")
    private List<Product> products = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Like> likes = new ArrayList<>();

    private String email;
    private String password;
    private String username;
    private String phoneNumber;
    private String nickname;
    private String profileImgUrl;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public User(String email, String password, String username, String phoneNumber, String nickname, String profileImgUrl) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.nickname = nickname;
        this.profileImgUrl = profileImgUrl;
        this.role = Role.USER;
    }
}
