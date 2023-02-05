package com.project.carrot.domain;

import lombok.Builder;
import lombok.Data;

import java.util.Collections;

@Data
public class ItemDTO {
    private Long USERID;
    private String TITLE;
    private String CONTENT;
    private int PRICE;
    private String CATEGORY;


    @Builder
    public ItemDTO(Long USERID,String TITLE,String CONTENT, int PRICE,String CATEGORY){
        this.USERID=USERID;
        this.TITLE=TITLE;
        this.CONTENT=CONTENT;
        this.CATEGORY=CATEGORY;
        this.PRICE=PRICE;
    }

    public MemberItem toEntity(){
        return MemberItem.builder()
                .UserId(USERID)
                .ItemTitle(TITLE)
                .ItemContent(CONTENT)
                .ItemPrice(PRICE)
                .ItemCategory(CATEGORY)
                .build();
    }
}
