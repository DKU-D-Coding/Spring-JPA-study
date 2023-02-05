package com.project.carrot.controller;

import com.project.carrot.Service.MemberItemService;
import com.project.carrot.domain.*;
import com.project.carrot.repository.MemberItemRepository;
import com.project.carrot.repository.MemberRepository;
import com.project.carrot.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    @PostMapping(value = "/itemAppend")
    public MemberItem appendItem(@RequestBody ItemDTO itemDTO) {
        MemberItem memberItem = itemDTO.toEntity();
        Optional<Member> member = memberItemRepository.findByUserId(itemDTO.getUSERID());
        log.info(member.get().getUserEmail());
        memberRepository.findByEmail(member.get().getUserEmail())
                .orElseThrow(() -> new IllegalArgumentException("유저 정보가 없습니다."));

        //mypage 구현을 위해 member에 addMemberItem 추가해야함
        log.info("상품 추가");
        return memberItemRepository.save(memberItem);
    }

}
