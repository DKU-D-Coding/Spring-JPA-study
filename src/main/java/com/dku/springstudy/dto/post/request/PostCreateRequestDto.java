package com.dku.springstudy.dto.post.request;

import com.dku.springstudy.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PostCreateRequestDto {
    private String title;
    private int price;
    private String content;
    private List<String> categories;
}
