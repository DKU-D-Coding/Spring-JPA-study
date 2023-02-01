package com.dku.springstudy.domain.constant;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum Category {

    DIGITAL("디지털기기"), LIFE_APPLIANCE("생활가전"), FURNITURE_INTERIOR("가구/인테리어"), CHILD_GOODS("유아용품"),
    LIFE_FOOD("생활/가공식품"), CHILD_BOOK("유아도서"), WOMEN_DRESS("여성의류"), MEN_DRESS_ETC("남성패션/잡화"), GAME_HOBBY("게임/취미"),
    BEAUTY("뷰티/미용"), ANIMAL("반려동물용품"), BOOK_TICKET_MUSIC("도서/티켓/음반"), ETC("기타중고물품"), CAR("중고차");

    private final String name;

    // 캐싱해두므로 조회할 때마다 모든 값 순회하지 않는다.
    private static final Map<String, Category> BY_NAME =
            Stream.of(values()).collect(Collectors.toMap(Category::name, e -> e));

    public static Category valueOfName(String name) {
        return BY_NAME.get(name);
    }
}
