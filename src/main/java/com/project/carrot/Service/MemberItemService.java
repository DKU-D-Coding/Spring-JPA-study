package com.project.carrot.Service;

import com.project.carrot.domain.MemberItem;
import com.project.carrot.repository.MemberItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
public class MemberItemService {
    /*
    * 상품 등록 페이지 구현
    * */

    public final MemberItemRepository memberItemRepository;

    public MemberItem append(MemberItem memberItem){
        return memberItemRepository.save(memberItem);
    }

}
