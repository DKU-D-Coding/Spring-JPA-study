package com.dku.springstudy.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ImageFile extends BaseEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    public static ImageFile createImageFile(String imageUrl){
        ImageFile imageFile = new ImageFile();
        imageFile.imageUrl = imageUrl;
        return imageFile;
    }

    public void updateItem(Item item){
        this.item = item;
        item.getImages().add(this);
    }
}
