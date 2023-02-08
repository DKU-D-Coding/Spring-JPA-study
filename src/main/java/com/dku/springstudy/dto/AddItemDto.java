package com.dku.springstudy.dto;

import com.dku.springstudy.domain.ImageFile;
import com.dku.springstudy.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddItemDto {
    private String title;
    private Category category;
    private int price;
    private String content;
}
