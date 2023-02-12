package com.cha.carrotApi.DTO.post;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NotBlank
public class PostCreateResponse {
    private Long id;
    private String title;
    private String content;
}
