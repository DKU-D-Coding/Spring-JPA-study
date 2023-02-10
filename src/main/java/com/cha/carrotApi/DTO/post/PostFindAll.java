package com.cha.carrotApi.DTO.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostFindAll {
    private List<PostSimpleDto> posts;
    private PageInfoDto pageInfoDto;

    public static PostFindAll toDto(List<PostSimpleDto> posts, PageInfoDto pageInfoDto){
        return new PostFindAll(posts, pageInfoDto);
    }
}
