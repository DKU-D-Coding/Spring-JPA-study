package com.cha.carrotApi.domain.User;

import com.cha.carrotApi.domain.BaseTimeEntity;
import com.cha.carrotApi.domain.Post.Post;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@Table(name = "USERS")
public class User extends BaseTimeEntity {
    @Id
    @Column(name = "USER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "EMAIL", length = 45, unique = true)
    private String email;

    @Column(name = "PASSWORD", length = 100)
    private String password;

    @Column(name = "NICKNAME", length = 45)
    private String nickname;

    @Column(name = "PHONE_NUMBER", length = 20)
    private String phonenumber;

    @Column(name = "ROLE")
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<Post> posts = new ArrayList<>();

//    @ManyToMany(mappedBy = "interest_posts")
//    @Column(name = "INTEREST_POST")
//    private List<Post> interests = new ArrayList<>();

    @Builder
    private User(String email, String nickname, String password, String phone_number) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.phonenumber = phonenumber;
        this.role = Role.USER;
    }
    public void encodePassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(password);
    }

    public void addUserAuthority() {
        this.role = Role.USER;
    }
}
