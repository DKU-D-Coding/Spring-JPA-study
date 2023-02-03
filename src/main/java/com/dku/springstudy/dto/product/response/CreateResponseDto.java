package com.dku.springstudy.dto.product.response;

import com.dku.springstudy.domain.constant.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class CreateResponseDto {

    @Schema(example = "https://d-coding.s3.ap-northeast-2.amazonaws.com/67a29351-9339-42b0-ba46-da6584ab93cc.jpg", description = "업로드한 사진들의 url")
    private List<String> fileUrls;

    @Schema(example = "옷 판매", description = "상품 글 제목")
    private String title;

    @Schema(example = "WOMEN_DRESS", description = "상품 카테고리")
    private Category category;

    @Schema(example = "10000", description = "상품 가격")
    private Integer price;

    @Schema(example = "1년 사용했습니다.", description = "상품 글 내용")
    private String content;

    public static CreateResponseDto of(List<String> fileUrls, String title, Category category, Integer price, String content) {
        return new CreateResponseDto(fileUrls, title, category, price, content);
    }
}
