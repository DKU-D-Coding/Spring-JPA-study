package com.dku.springstudy.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Category {
    DIGITAL_DEVICE("디지털기기"), HOME_APPLIANCE("생활가전"), FURNITURE_INTERIOR("가구/인테리어"),
    BABY_PRODUCTS("유아용품"), LIFE_PROCESSED_FOOD("생활/가공식품"), CHILDREN_BOOKS("유아도서"), WOMEN_CLOTHING("여성의류"),
    MEN_CLOTHING_STUFF("남성패션/잡화"), GAMES_HOBBY("게임/취미"), BEAUTY("뷰티/미용"), COMPANION_ANIMAL_STUFF("반려동물용품"),
    BOOK_TICKET_MUSIC("도서/티켓/음반"), OTHER_USED_ITEMS("기타중고물품"), USED_CAR("중고차");

    @JsonCreator
    public static Category from(String s) {
        return Category.valueOf(s.toUpperCase());
    }

    private final String label;

    Category(String label) {
        this.label = label;
    }

    public String label() {
        return label;
    }
}
