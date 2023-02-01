package com.dku.springstudy.dto;

import com.dku.springstudy.model.Board;
import com.dku.springstudy.model.Category;
import lombok.Data;

@Data
public class BoardResponseDTO {

    private Long id;
    private String title;
    private String intro;
    private Category category;
    private int price;

    public BoardResponseDTO(Board board){
        id = board.getId();
        title = board.getTitle();
        intro = board.getIntro();
        category = board.getCategory();
        price = board.getPrice();
    }
}
