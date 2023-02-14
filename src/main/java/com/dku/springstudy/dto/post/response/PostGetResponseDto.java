package com.dku.springstudy.dto.post.response;

import com.dku.springstudy.domain.Member;
import com.dku.springstudy.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostGetResponseDto {
    private String title;
    private int price;
    private String content;

    private Long memberId;

    private Long postId;

    public PostGetResponseDto(Post post){
        this.title = post.getTitle();
        this.content = post.getContent();
        this.price = post.getPrice();
        this.memberId = post.getMember().getId();
        this.postId = post.getId();
    }
}
