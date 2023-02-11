package com.dku.springstudy.dto;

import com.dku.springstudy.dto.user.ImageResponseDTO;
import com.dku.springstudy.model.Category;
import com.dku.springstudy.model.Items;
import com.dku.springstudy.model.User;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ItemDetailsResponseDTO {
    private Long id;
    private List<ImageResponseDTO> images;
    private ItemUserResponseDTO user;
    private String title;
    private String intro;
    private Category category;
    private int price;
    private LocalDateTime modifiedDate;
    private int likesCount;

    public ItemDetailsResponseDTO(Items items){
        id = items.getId();
        images = items.getImages().stream()
                .map(i->new ImageResponseDTO(i))
                .collect(Collectors.toList());
        user = new ItemUserResponseDTO(items.getUser());
        title = items.getTitle();
        intro = items.getIntro();
        category = items.getCategory();
        price = items.getPrice();
        modifiedDate = items.getModifiedDate();
        likesCount = items.getTotalLikes();
    }
}
