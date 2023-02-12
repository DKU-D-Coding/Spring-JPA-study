package com.dku.springstudy.dto;

import com.dku.springstudy.enums.ItemStatus;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ItemDto {
    @ApiModelProperty(value = "상품 id", dataType = "Long")
    private Long id;

    @ApiModelProperty(value = "상품 이미지 경로들", dataType = "List<String>")
    private List<String> imagePath;

    @ApiModelProperty(value = "상품 제목", dataType = "string")
    private String title;

    @ApiModelProperty(value = "상품 내용", dataType = "string")
    private String content;

    @ApiModelProperty(value = "상품 가격", dataType = "price")
    private int price;

    @ApiModelProperty(value = "상품 좋아요 개수", dataType = "int")
    private int likeCount;

    @ApiModelProperty(value = "상품 상태", dataType = "string")
    private ItemStatus itemStatus;
}
