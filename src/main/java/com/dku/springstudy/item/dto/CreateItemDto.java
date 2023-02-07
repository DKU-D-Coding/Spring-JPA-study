package com.dku.springstudy.item.dto;

import com.dku.springstudy.item.entity.Category;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
public class CreateItemDto {
    private String title;
    private Long price;
    private Category category;
    private String content;

    private List<MultipartFile> files = new ArrayList<>();
}
