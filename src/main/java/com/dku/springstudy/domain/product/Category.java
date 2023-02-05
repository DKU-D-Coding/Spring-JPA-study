package com.dku.springstudy.domain.product;

import lombok.Getter;

@Getter
public enum Category {
    /*
    ‘디지털기기’, ‘생활가전’, ‘가구/인테리어’, ‘유아동’, ‘생활/가공식품’,
    ‘유아도서’, ‘스포츠/레저’, ‘여성잡화’, ‘여성의류’, ‘남성패션/잡화’,
    ‘게임/취미’, ‘뷰티/미용’, ‘반려동물용품’, ‘도서/티켓/음반’, ‘식물’,
    ‘기타 중고물품’, ‘중고차’
     */
    DIGITAL_DEVICE("디지털기기"),
    HOME_ELECTRONIC("생활가전"),
    FURNITURE_INTERIOR("가구/인테리어"),
    CHILD("유아동"),
    LIVING_PROCESSED_FOODS("생활/가공식품"),
    CHILD_BOOK("유아도서"),
    SPORTS_LEISURE("스포츠/레저"),
    WOMAN_GOODS("여성잡화"),
    WOMAN_CLOTHES("여성의류"),
    MAN_FASHION_GOODS("남성패션/잡화"),
    GAME_HOBBY("게임/취미"),
    BEAUTY("뷰티/미용"),
    PET_GOODS("반려동물용품"),
    BOOK_TICKET_RECORD("도서/티켓/음반"),
    PLANT("식물"),
    USED_ETC("기타 중고물품"),
    USED_CAR("중고차");

    private final String korCategory;

    Category(String korCategory) {
        this.korCategory = korCategory;
    }
}
