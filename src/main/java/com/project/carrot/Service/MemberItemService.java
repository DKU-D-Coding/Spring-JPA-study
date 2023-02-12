package com.project.carrot.Service;

import com.project.carrot.domain.ItemDTO;
import com.project.carrot.domain.MemberItem;
import com.project.carrot.repository.MemberItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public class MemberItemService {
    /*
    * 상품 등록 페이지 구현
    * */

    public final MemberItemRepository memberItemRepository;

    @Transactional
    public MemberItem append(MemberItem memberItem){
        return memberItemRepository.save(memberItem);
    }

    @Transactional
    public MemberItem saled(Long id){
        MemberItem memberItem=memberItemRepository.findByItemId(id)
                .orElseThrow(()->new RuntimeException("존재하지 않는 유저입니다."));
        //memberItem=memberItem.toBuilder().ItemForSale(false).build();
        memberItem.update(false);

        System.out.println(memberItem.toString());
        return memberItem;
    }
}
