package com.dku.springstudy.domain;

import com.dku.springstudy.domain.common.BaseTimeEntity;
import com.dku.springstudy.domain.constant.Role;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    private Integer status;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String ImgUrl;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> productList = new ArrayList<>();

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

    // 회원 정보 수정
    public void update(String nickname, String ImgUrl) {
        this.nickname = nickname;
        this.ImgUrl = ImgUrl;
    }

    // 프로필 사진 삭제
    public void deleteImg() {
        this.ImgUrl = null;
    }
}
