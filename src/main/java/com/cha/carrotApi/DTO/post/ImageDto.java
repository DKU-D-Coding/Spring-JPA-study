package com.cha.carrotApi.DTO.post;

import com.cha.carrotApi.domain.Post.Image;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ImageDto{
    private int id;
    private String originName;
    private String uniqueName;

    public static ImageDto toDto(Image image) {
        return new ImageDto(image.getId(), image.getOriginName(), image.getUniqueName());
    }
}
