package com.dku.springstudy.domain;

import com.dku.springstudy.enums.Category;
import com.dku.springstudy.enums.ItemStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String title;
    private String content;
    private int price;

    @OneToMany(mappedBy = "item")
    private List<ImageFile> images = new ArrayList<>();

    @Enumerated(value = EnumType.STRING)
    private ItemStatus status;

    @Enumerated(EnumType.STRING)
    private Category category;


    public static Item createItem(Member member, String title, String content, int price, Category category){
        Item item = new Item();
        item.member = member;
        item.title = title;
        item.content = content;
        item.price = price;
        item.category = category;
        item.status = ItemStatus.SELLING; // 상품 처음 등록시에는 SELLING(판매중) 상태

        return item;
    }

    public void addImage(ImageFile image){
        images.add(image);
    }

}
