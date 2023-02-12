package com.dku.springstudy.dto.product.response;

import com.dku.springstudy.domain.Product;
import com.dku.springstudy.domain.User;
import com.dku.springstudy.domain.constant.Category;
import com.dku.springstudy.domain.constant.Status;
import com.dku.springstudy.s3.domain.File;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ProductInfoResponseDto {

    @Schema(example = "1", description = "상품 아이디")
    private Long productId;

    @Schema(example = "자전거 판매", description = "상품 글 제목")
    private String title;

    @Schema(example = "자전거 판매합니다.", description = "상품 글 내용")
    private String content;

    @Schema(example = "100000", description = "상품 가격")
    private Integer price;

    @Schema(example = "EXERCISE", description = "상품 카테고리")
    private Category category;

    @Schema(example = "2", description = "상품 좋아요 개수")
    private Integer likeCount;

    @Schema(example = "PROGRESS", description = "상품 상태")
    private Status status;

    @Schema(description = "상품 사진")
    private List<String> imageUrls;

    @Schema(description = "상품 판매자")
    private userInfoDto writer;

    @Schema(description = "판매자의 판매 중인 상품")
    private List<SubItemInfoDto> itemList;

    @Schema(example = "true", description = "판매자 여부")
    private boolean isWriter;

    @Schema(example = "2023-02-11 01:01:01", description = "상품 글 수정시간")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    @Schema(description = "판매자 정보")
    @Getter
    public static class userInfoDto {

        @Schema(example = "1", description = "판매자 아이디(PK)")
        private Long userId;

        @Schema(example = "jjeong", description = "판매자 닉네임")
        private String nickname;

        @Schema(description = "판매자 프로필 사진")
        private String imgUrl;

        public userInfoDto(User user) {
            this.userId = user.getId();
            this.nickname = user.getNickname();
            this.imgUrl = user.getImgUrl();
        }
    }

    @Schema(description = "판매자가 판매 중인 상품 정보")
    @Getter
    public static class SubItemInfoDto {

        @Schema(example = "1", description = "상품 아이디")
        private Long productId;

        @Schema(description = "")
        private String imageUrl;

        @Schema(example = "선풍기 판매", description = "상품 글 제목")
        private String title;

        @Schema(example = "50000", description = "상품 글 가격")
        private Integer price;

        public SubItemInfoDto(Product product) {
            this.productId = product.getId();
            this.imageUrl = product.getImageUrls().get(0).getUrl();
            this.title = product.getTitle();
            this.price = product.getPrice();
        }
    }

    public static ProductInfoResponseDto from(User user, Product product) {
        return new ProductInfoResponseDto(
                product.getId(),
                product.getTitle(),
                product.getContent(),
                product.getPrice(),
                product.getCategory(),
                product.getLikeCount(),
                product.getStatus(),
                product.getImageUrls().stream().map(File::getUrl).collect(Collectors.toUnmodifiableList()),
                new userInfoDto(product.getUser()),
                product.getUser().getProductList().stream().map(SubItemInfoDto::new).collect(Collectors.toUnmodifiableList()),
                product.getUser().getId().equals(user.getId()),
                product.getModifiedAt()
        );
    }
}