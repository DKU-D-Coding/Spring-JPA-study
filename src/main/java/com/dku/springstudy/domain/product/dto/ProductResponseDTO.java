package com.dku.springstudy.domain.product.dto;

import com.dku.springstudy.domain.product.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDTO {
    private Long productId;
    private String productName;
    private Integer cost;
    private String contents;
    private Long userId;
    //like추가

    public ProductResponseDTO(Product product) {
        this.productId = product.getId();
        this.productName = product.getProductName();
        this.cost = product.getCost();
        this.contents = product.getContents();
        this.userId = product.getUser().getId();
    }
}
