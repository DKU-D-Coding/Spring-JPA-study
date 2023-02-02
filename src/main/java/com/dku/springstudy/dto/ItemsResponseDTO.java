package com.dku.springstudy.dto;

import com.dku.springstudy.model.Images;
import com.dku.springstudy.model.Items;
import com.dku.springstudy.model.Category;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ItemsResponseDTO {

    private Long id;

    private List<Images> images;
    private String title;
    private String intro;
    private Category category;
    private int price;

    private LocalDateTime modifiedDate;

    public ItemsResponseDTO(Items items){
        id = items.getId();
        images = items.getImages();
        title = items.getTitle();
        intro = items.getIntro();
        category = items.getCategory();
        price = items.getPrice();
        modifiedDate = items.getModifiedDate();
    }
}
