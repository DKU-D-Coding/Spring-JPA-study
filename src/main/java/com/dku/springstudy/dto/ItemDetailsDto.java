package com.dku.springstudy.dto;

import com.dku.springstudy.domain.Item;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class ItemDetailsDto {
    @ApiModelProperty(value = "판매자 닉네임", dataType = "string")
    private String sellerNickname;

    @ApiModelProperty(value = "상품 카테고리", dataType = "string")
    private String category;

    @ApiModelProperty(value = "상품 등록일(최근 수정일)", dataType = "LocalDateTime")
    private LocalDateTime lastModifiedDate;

    @ApiModelProperty(value = "상품 이미지 경로들", dataType = "List<String>")
    private List<String> imagePath;

    @ApiModelProperty(value = "상품 제목", dataType = "string")
    private String title;

    @ApiModelProperty(value = "상품 내용", dataType = "string")
    private String content;

    @ApiModelProperty(value = "상품 가격", dataType = "int")
    private int price;

    @ApiModelProperty(value = "판매자의 모든 상품 내역", dataType = "List<ItemDto>")
    private List<ItemDto> sellerItems;
}
