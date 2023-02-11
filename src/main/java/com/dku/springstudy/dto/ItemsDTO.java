package com.dku.springstudy.dto;

import lombok.Data;

import java.util.List;

@Data
public class ItemsDTO {

    private Long id;

    private List<String> imageUrls;
    private String title;
    private int price;
    private String category;
    private String intro;
}