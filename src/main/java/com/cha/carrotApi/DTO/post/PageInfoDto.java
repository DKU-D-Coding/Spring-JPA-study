package com.cha.carrotApi.DTO.post;

import com.cha.carrotApi.domain.Post.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PageInfoDto {
    private int totalPage;
    private int nowPage;
    private int numberOfElements;
    private boolean isNext;

    public PageInfoDto(Page<Post> result) {
        this.totalPage = result.getTotalPages();
        this.nowPage = result.getNumber();
        this.numberOfElements = result.getNumberOfElements();
        this.isNext = result.hasNext();
    }
}
