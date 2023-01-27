package com.dku.springstudy.item.entity;

/**
 * @author 최재민
 */
public enum Category {
    DIGITAL_DEVICE("디지털기기"),
    HOUSEHOLD_APPLIANCE("생활가전"),
    FURNITURE("가구"),
    CHILDREN("유아동"),
    PROCESSED_FOOD("생활/가공식품"),
    CHILDREN_BOOK("유아도서"),
    WOMEN_CLOTHING("여성의류"),
    MEN_CLOTHING("남성의류"),
    LEISURE("게임/취미"),
    BEAUTY("뷰티"),
    PET("반려동물용품"),
    BOOK("도서/티켓/음반"),
    PLANT("식물"),
    CAR("중고차"),
    OTHERS("기타 중고물품")
    ;


    private String name;
    Category(String name) {
        this.name = name;
    }
}
