package com.dku.springstudy.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "ITEM_LIKE")
public class ItemLike extends BaseEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public static ItemLike createItemLike(Item item, Member member){
        ItemLike itemLike = new ItemLike();
        itemLike.item = item;
        item.getLikes().add(itemLike);
        itemLike.member = member;
        return itemLike;
    }
}
