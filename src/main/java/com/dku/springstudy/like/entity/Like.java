package com.dku.springstudy.like.entity;

import com.dku.springstudy.global.BaseEntity;
import com.dku.springstudy.item.entity.Item;
import com.dku.springstudy.member.entity.Member;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Table(name = "likes")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Like extends BaseEntity {

    @EmbeddedId
    private LikeId likeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("memberId")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("itemId")
    private Item item;

    private boolean checked;


    /**
     * composite key 사용
     */
    @Embeddable
    @Getter @Setter
    @EqualsAndHashCode
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class LikeId implements Serializable {
        @Column(name = "item_id")
        private Long itemId;
        @Column(name = "member_id")
        private Long memberId;
    }

    public Like(Member member, Item item) {
        this.member = member;
        this.item = item;
        this.checked = true;
    }

    public void unCheckLike() {
        this.checked = false;
    }

    public void checkLike() {
        this.checked = true;
    }
}