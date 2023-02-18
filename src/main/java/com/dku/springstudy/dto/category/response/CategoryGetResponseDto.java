package com.dku.springstudy.dto.category.response;

import com.dku.springstudy.domain.Category;
import com.dku.springstudy.dto.post.response.PostGetResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;


@Getter
@AllArgsConstructor
public class CategoryGetResponseDto {
    private String category;
    private Timestamp created;

    private List<Long> posts;

    public CategoryGetResponseDto(Category category){
        this.category = category.getCategory();
        this.created = category.getCreated();
        this.posts = Optional.ofNullable(category.getPosts())
                .orElse(new ArrayList<>()).stream()
                .map(i -> i.getPost().getPost_id())
                .collect(Collectors.toList());
    }
}
