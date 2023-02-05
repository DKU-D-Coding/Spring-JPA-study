package com.dku.springstudy.dto.product.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CreateRequestDto {

    @Schema(example = "옷 판매", description = "상품 글 제목")
    @NotBlank
    private String title;

    @Schema(example = "WOMEN_DRESS", description = "상품 카테고리")
    @NotNull
    private String category;

    @Schema(example = "10000", description = "상품 가격")
    @Min(value = 0)
    private Integer price;

    @Schema(example = "1년 사용했습니다.", description = "상품 글 내용")
    @NotBlank
    private String content;
}
