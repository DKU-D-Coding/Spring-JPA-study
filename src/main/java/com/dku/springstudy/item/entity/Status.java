package com.dku.springstudy.item.entity;

/**
 * @author 최재민
 */

public enum Status {
    ON_SALE("판매중"),
    RESERVED("예약완료"),
    SOLD("판매완료");

    private String name;

    Status(String name) {
        this.name = name;
    }
}
