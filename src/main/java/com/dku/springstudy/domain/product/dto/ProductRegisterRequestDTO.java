package com.dku.springstudy.domain.product.dto;

import com.dku.springstudy.domain.product.Category;
import com.dku.springstudy.domain.product.Product;
import com.dku.springstudy.domain.product.ProductStatus;
import com.dku.springstudy.domain.user.User;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class ProductRegisterRequestDTO {
    @NonNull
    private String productName;
    @NonNull
    private Category category;
    @NonNull
    private Integer cost;
    @NonNull
    private String contents;
    @NonNull
    private String productImgUrl;

    @Builder
    public ProductRegisterRequestDTO(String productName, Category category, Integer cost, String contents, String productImgUrl) {
        this.productName = productName;
        this.category = category;
        this.cost = cost;
        this.contents = contents;
        this.productImgUrl = productImgUrl;
    }

    public Product toEntity(User user) {
        return Product.builder()
                .productName(this.getProductName())
                .category(this.getCategory())
                .cost(this.getCost())
                .contents(this.getContents())
                .productImgUrl(this.getProductImgUrl())
                .productStatus(ProductStatus.ON_SALE)
                .user(user)
                .build();
    }
}
