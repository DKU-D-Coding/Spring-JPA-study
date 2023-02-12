package com.project.carrot.domain;

import com.project.carrot.repository.MemberItemRepository;
import lombok.Builder;
import lombok.Data;


@Data
public class ItemDTO {
    private Long USERID;
    private String TITLE;
    private String CONTENT;
    private int PRICE;
    private String CATEGORY;
    private Member member;
    @Builder.Default
    private boolean ITEMFORSALE=true;

    private MemberItemRepository memberItemRepository;


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
                //.member(memberItemRepository.findByUserId(USERID).orElseThrow(()->new RuntimeException("존재하지 않는 member")))
                .build();
    }

}
