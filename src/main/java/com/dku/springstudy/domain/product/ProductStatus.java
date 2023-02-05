package com.dku.springstudy.domain.product;

import lombok.Getter;

@Getter
public enum ProductStatus {
    ON_SALE("판매중"), SOLD_OUT("판매완료"), RESERVED("예약중");

    private final String korStatus;

    ProductStatus(String korStatus) {
        this.korStatus = korStatus;
    }

}
