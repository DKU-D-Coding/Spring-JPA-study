package com.dku.springstudy.domain.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductListResponseDTO {
    private ProductResponseDTO selectProduct;
    private List<ProductResponseDTO> otherProductList;
}
