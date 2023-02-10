package com.cha.carrotApi.DTO.post;

import com.cha.carrotApi.domain.Post.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponseDto {
    private Long id;
    private String writer_nickname;
    private String title;
    private String content;
    private int likeCount;
    private int interested;
    private List<ImageDto> images;
    private LocalDateTime createdAt;

    public static PostResponseDto toDto(Post post, String writer_nickname) {
        return new PostResponseDto(
                post.getId(),
                writer_nickname,
                post.getTitle(),
                post.getContent(),
                post.getLikeCount(),
                post.getInterested(),
                post.getImages().stream().map(i -> ImageDto.toDto(i)).collect(Collectors.toList()),
                post.getCreateDate()
        );
    }
}
