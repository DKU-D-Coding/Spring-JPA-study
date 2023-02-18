package com.dku.springstudy.dto.post.response;

import com.dku.springstudy.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostCreateResponseDto {
    private String title;
    private int price;
    private String content;

    public PostCreateResponseDto(Post post){
        this.title = post.getTitle();
        this.content = post.getContent();
        this.price = post.getPrice();
    }
}
