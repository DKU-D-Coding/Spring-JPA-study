package com.dku.springstudy.dto.post.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostCreateRequestDto {
    private String title;
    private int price;
    private String content;
}
