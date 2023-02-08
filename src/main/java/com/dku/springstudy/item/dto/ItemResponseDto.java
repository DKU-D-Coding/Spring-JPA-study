package com.dku.springstudy.item.dto;

import com.dku.springstudy.item.entity.Category;
import com.dku.springstudy.item.entity.Image;
import com.dku.springstudy.item.entity.Item;
import com.dku.springstudy.item.entity.Status;
import com.dku.springstudy.like.entity.Like;
import com.dku.springstudy.member.entity.Member;

import javax.persistence.*;
import java.util.List;

public class ItemResponseDto {
    private String title;

    private Long price;

    private List<Image> image;

    private String seller;

    private Category category;

    private String content;

    private Status status;

    private int likeCount;

    public ItemResponseDto(Item item) {
        this.title = item.getTitle();
        this.content = item.getContent();
        this.image = item.getImage();
        this.seller = item.getSeller().getName();
        this.category = item.getCategory();
        this.status = item.getStatus();
        this.likeCount = item.getLikes().size();
    }
}
