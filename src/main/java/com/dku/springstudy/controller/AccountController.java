package com.dku.springstudy.controller;

import com.dku.springstudy.model.Member;
import com.dku.springstudy.model.MemberForm;
import com.dku.springstudy.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/account")
@RequiredArgsConstructor
public class AccountController {
    private final MemberService memberService;

    @PostMapping("/signup")
    public String signup(MemberForm memberForm) {
        Member newMember = new Member();
        newMember.setEmail(memberForm.getEmail());
        newMember.setPassword(memberForm.getPassword());
        newMember.setNickname(memberForm.getNickname());
        memberService.join(newMember);
        return "success";
    }
}
