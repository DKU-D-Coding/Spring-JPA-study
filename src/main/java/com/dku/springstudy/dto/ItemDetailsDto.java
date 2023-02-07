package com.dku.springstudy.dto;

import com.dku.springstudy.domain.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class ItemDetailsDto {
    private String sellerNickname;
    private String category;
    private LocalDateTime lastModifiedDate;
    private List<String> imagePath;
    private String title;
    private String content;
    private int price;
    private List<ItemDto> sellerItems;
}
