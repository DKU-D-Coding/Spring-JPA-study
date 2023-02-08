package com.dku.springstudy.dto;

import com.dku.springstudy.enums.ItemStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ItemDto {
    private Long id;
    private List<String> imagePath;
    private String title;
    private String content;
    private int price;
    private int likeCount;
    private ItemStatus itemStatus;
}
