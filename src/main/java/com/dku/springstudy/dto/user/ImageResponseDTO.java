package com.dku.springstudy.dto.user;

import com.dku.springstudy.model.Images;
import lombok.Data;

import java.util.List;

@Data
public class ImageResponseDTO {
    private String urls;

    public ImageResponseDTO(Images images){
        urls = images.getUrl();
    }
}