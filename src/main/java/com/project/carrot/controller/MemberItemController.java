package com.project.carrot.controller;

import com.project.carrot.MyInterceptor;
import com.project.carrot.Service.MemberItemService;
import com.project.carrot.domain.*;
import com.project.carrot.repository.MemberItemRepository;
import com.project.carrot.repository.MemberRepository;
import com.project.carrot.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MemberItemController {
    private final MemberItemRepository memberItemRepository;
    private final MemberItemService memberItemService;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;


    @GetMapping(value = "/item") //추후에 /user/item으로 변경해서 권한 있는 사람만 가능하게
    public List<MemberItem> showItemList() {
        return memberItemRepository.findAll();
    }


    @PostMapping(value = "/item/sellItem")
    public MemberItem sellItem(@RequestBody ItemDTO itemDTO) {
        MemberItem memberItem = itemDTO.toEntity();
        Member member = memberItemRepository.findByUserId(memberItem.getUserId())
                .orElseThrow(()-> new IllegalArgumentException("유저 정보가 없습니다."));
        
        log.info(member.getUserEmail());
        log.info("상품 추가");

        return memberItemRepository.save(memberItem);
    }

    @RequestMapping(value="/item/buyItem") //URL을 통해 memberItem 반환
    public MemberItem buyItemPage(@RequestParam("itemId") Long id) {
        log.info("인자의 값", id);
        MemberItem memberItem = memberItemRepository.findByItemId(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 ItemID"));

        return memberItem;
    }

    @PostMapping(value = "/item/buyItem")
    public MemberItem buyItem(@RequestParam("itemId") Long id) {
        MemberItem memberItem = memberItemRepository.findByItemId(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 ItemID"));
        try {
            if (!memberItem.getItemForSale()) {
                log.info("이미 판매된 상품입니다.");
            }
        }
        catch (Exception e){
            e.getMessage();
        }
        return memberItemService.saled(id);
    }

    //mypage
    @GetMapping(value="/mypage/sellItem")
    public List<MemberItem> mySellList(@RequestParam("userId") Long id){
        Member member=memberRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("유저 정보가 존재하지 않습니다."));
        log.info("member",member.getUserId());
        List<MemberItem> memberItemList = memberItemRepository.findAllByUserId(member.getUserId());
        log.info("memberItem", memberItemRepository.findByUserId(member.getUserId()));

        return memberItemList;
    }
}
