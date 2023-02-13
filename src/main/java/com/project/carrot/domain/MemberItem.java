package com.project.carrot.domain;

import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor //필요할 듯
@Entity //객체라는 것을 알림
@ToString
@Builder
public class MemberItem {
    @Id
    @GeneratedValue
    private Long ItemId;
    private String ItemTitle;
    private String ItemContent;
    private int ItemPrice;
    @Builder.Default
    private String ItemApplyDate=null; //객체 생성 후에 JPA Auditing을 이용해서 반환할 것
    @Builder.Default
    private Boolean ItemForSale=true; //올리면 판매하는 상황이기 때문에
    //private String ItemLocation; //Member에서 가져와야하는데 아직 구현하지 않았음
    @Builder.Default
    private int ItemEmphathy=0; //공감을 클릭하면 올라갈 수 있게 0으로 초기화
    private String ItemCategory;
    //private Member ItemSeller; //ManyToOne과 OneToMany 공부 후 결정
    @Builder.Default
    private int ItemChatting=0; //채팅한 횟수
    @Builder.Default
    private int ItemViews=0; //조회한 횟수
    @Builder.Default
    private String ItemUpdated=null;
    @Builder.Default
    private String ItemDeleted=null;
    //@Column(name="UserId",insertable = false,updatable = false)
    private Long UserId;
    @Builder.Default
    private Long BuyUserId=null;

    public void update(boolean ItemForSale, Long BuyUserId){
        this.ItemForSale=ItemForSale;
        this.BuyUserId=BuyUserId;
    }

}