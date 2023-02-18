package com.dku.springstudy.dto.post.response;

import com.dku.springstudy.domain.Category;
import com.dku.springstudy.domain.Post;
import com.dku.springstudy.domain.PostCategory;
import com.dku.springstudy.dto.category.response.CategoryGetResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class PostGetResponseDto {
    private String title;
    private int price;
    private String content;

    private Long memberId;

    private Long postId;

    private List<String> categories;

    public PostGetResponseDto(Post post){
        this.title = post.getTitle();
        this.content = post.getContent();
        this.price = post.getPrice();
        this.memberId = post.getMember().getMember_id();
        this.postId = post.getPost_id();
        this.categories = Optional.ofNullable(post.getCategories())
                .orElse(new ArrayList<>()).stream()
                .map(i -> i.getCategory().getCategory())
                .collect(Collectors.toList());
    }
}
