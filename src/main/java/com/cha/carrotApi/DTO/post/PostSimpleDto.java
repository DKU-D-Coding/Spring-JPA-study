package com.cha.carrotApi.DTO.post;

import com.cha.carrotApi.domain.Post.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostSimpleDto {
    private Long id;
    private String title;
    private String content;
    private int liked;
    private int interested;

    public PostSimpleDto toDto(Post post) {
        return new PostSimpleDto(post.getId(), post.getTitle(), post.getUser().getNickname(), post.getLikeCount(),
                post.getInterested());
    }
}
