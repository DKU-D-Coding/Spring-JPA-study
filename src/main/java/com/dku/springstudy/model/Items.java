package com.dku.springstudy.model;

import com.dku.springstudy.dto.ItemsDTO;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter @Builder
@Entity
public class Items extends BaseTimeEntity {
    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "items",cascade = CascadeType.ALL)
    private List<Likes> likes = new ArrayList<>();

    @OneToMany(mappedBy = "items", cascade = CascadeType.ALL)
    private List<Images> images = new ArrayList<>();

    private String title;
    @Lob
    private String intro;

    @Enumerated(EnumType.STRING)
    private Category category;

    private int price;

    @Enumerated(EnumType.STRING)
    private ItemStatus itemStatus;

    public void modifyItems(ItemsDTO itemsDTO) {
        this.title = itemsDTO.getTitle();
        this.price = itemsDTO.getPrice();
        this.intro = itemsDTO.getIntro();
        this.category = Category.valueOf(itemsDTO.getCategory());
    }

    public void addItemWithImage(Images image){
        images.add(image);
        image.setItems(this);
    }

    public void addLike(Likes likes){
        this.likes.add(likes);
    }

    public void deleteLike(Likes likes){
        this.likes.remove(likes);
    }

    public int getTotalLikes(){
        return likes.size();
    }
}
