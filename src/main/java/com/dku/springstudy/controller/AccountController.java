package com.dku.springstudy.controller;

import com.dku.springstudy.model.Member;
import com.dku.springstudy.dto.SignupDTO;
import com.dku.springstudy.model.Role;
import com.dku.springstudy.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/account")
@RequiredArgsConstructor
public class AccountController {
    private final BCryptPasswordEncoder passwordEncoder;
    private final MemberService memberService;

    @PostMapping("/signup")
    public String signup(SignupDTO memberForm) {
        String rawPassword = memberForm.getPassword();
        String encodedPassword = passwordEncoder.encode(rawPassword);

        Member newMember = new Member();
        newMember.setEmail(memberForm.getEmail());
        newMember.setPassword(encodedPassword);
        newMember.setNickname(memberForm.getNickname());
        newMember.setRole(Role.USER);
        memberService.join(newMember);

        return "success";
    }
}
