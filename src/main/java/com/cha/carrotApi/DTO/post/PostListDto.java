package com.cha.carrotApi.DTO.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@AllArgsConstructor
public class PostListDto {
    private Integer totalElement;
    private Integer totalPages;
    private boolean hasNext;
    private List<PostSimpleDto> postList;

    public static PostListDto toDto(Page<PostSimpleDto> page) {
        return new PostListDto(page.getTotalPages(), (int)page.getTotalElements(), page.hasNext(), page.getContent());
    }
}
