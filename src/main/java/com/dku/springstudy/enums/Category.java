package com.dku.springstudy.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Category {

    DIGITAL("DIGITAL"), ELECTRONIC("ELECTRONIC"), FURNITURE_INTERIOR("FURNITURE_INTERIOR"), KIDS("KIDS"), LIFE_FOOD("LIFE_FOOD"), BOOK_KIDS("BOOK_KIDS"), SPORTS("SPORTS"),
    WOMAN_MERCHANDISE("WOMAN_MERCHANDISE"), WOMAN_CLOTHES("WOMAN_CLOTHES"), MAN_MERCHANDISE_CLOTHES("MAN_MERCHANDISE_CLOTHES"),
    GAME_HOBBY("GAME_HOBBY"), BEAUTY_HAIR("BEAUTY_HAIR"), PET("PET"), BOOK_TICKET_MUSIC("BOOK_TICKET_MUSIC"), PLANTS("PLANTS"), USED_THINGS("USED_THINGS"), USED_CAR("USED_CAR");

    @JsonValue
    private final String category;
}
