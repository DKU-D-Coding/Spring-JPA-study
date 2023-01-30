package com.dku.springstudy.like.entity;

import com.dku.springstudy.global.BaseEntity;
import com.dku.springstudy.item.entity.Item;
import com.dku.springstudy.Member.entity.Member;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Table(name = "likes")
public class Like extends BaseEntity {

    @EmbeddedId
    private LikeId likeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("memberId")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("itemId")
    private Item item;


    /**
     * composite key 사용
     */
    @Embeddable
    @Getter @Setter
    @EqualsAndHashCode
    public static class LikeId implements Serializable {
        @Column(name = "item_id")
        private Long itemId;
        @Column(name = "member_id")
        private String memberId;
    }
}