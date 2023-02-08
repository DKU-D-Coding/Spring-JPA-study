package com.dku.springstudy.item.entity;

import com.dku.springstudy.global.BaseEntity;
import com.dku.springstudy.like.entity.Like;
import com.dku.springstudy.member.entity.Member;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 최재민
 */
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class Item extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String title;

    private Long price;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> image = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member seller;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Lob
    private String content;

    private Status status;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> likes = new ArrayList<>();

    public void addLike(Like like) {
        this.likes.add(like);
    }
}
