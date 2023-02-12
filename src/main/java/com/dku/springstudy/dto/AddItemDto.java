package com.dku.springstudy.dto;

import com.dku.springstudy.domain.ImageFile;
import com.dku.springstudy.enums.Category;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddItemDto {

    @ApiModelProperty(value = "상품 제목", dataType = "string")
    private String title;
    @ApiModelProperty(value = "상품 카테고리", dataType = "string")
    private Category category;
    @ApiModelProperty(value = "상품 가격", dataType = "int")
    private int price;
    @ApiModelProperty(value = "상품 내용", dataType = "string")
    private String content;
}
