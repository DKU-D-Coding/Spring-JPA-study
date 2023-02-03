package com.project.carrot.domain;

import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@AllArgsConstructor
@NoArgsConstructor //필요할 듯
@Entity //객체라는 것을 알림
@ToString
@Builder
public class MemberItem {
    @Id
    @GeneratedValue
    private int ItemId;
    private String UserEmail;//외래키
    private String ItemTitle;
    private String ItemContent;
    private int ItemPrice;
    private String ItemApplyDate;
    private Boolean ItemForSale;
    private String ItemLocation;
    private String ItemEmphathy;
    private String ItemCategory;
    private String ItemSeller;
    private int ItemChatting;
    private int ItemViews;
    @Builder.Default
    private String ItemUpdated=null;
    @Builder.Default
    private String ItemDeleted=null;

}