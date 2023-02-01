package com.dku.springstudy.dto;

import lombok.Data;

import java.util.List;

@Data
public class BoardDTO {

    private List<String> imageUrls;
    private String title;
    private int price;
    private String intro;
}