package com.dku.springstudy.member.entity;

import com.dku.springstudy.global.BaseEntity;
import com.dku.springstudy.item.entity.Item;
import com.dku.springstudy.like.entity.Like;
import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * @author 최재민
 */
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
public class Member extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;

    private String name;

    private String nickname;

    @Enumerated(EnumType.STRING)
    private MemberROLE memberRole;

    @Column(name = "phone_number")
    private String phoneNumber;

    private String profileImageUrl;

    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Item> products;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> likes;

}
