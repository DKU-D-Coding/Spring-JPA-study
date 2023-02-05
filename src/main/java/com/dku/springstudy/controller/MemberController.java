package com.dku.springstudy.controller;

import com.dku.springstudy.dto.member.request.LoginRequestDto;
import com.dku.springstudy.dto.member.request.MembershipRequestDto;
import com.dku.springstudy.dto.member.response.LoginResponseDto;
import com.dku.springstudy.dto.member.response.WithdrawResponseDto;
import com.dku.springstudy.security.CustomUserDetails;
import com.dku.springstudy.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MemberController {
    private final MemberService memberService;

    // 회원가입
    @PostMapping("/membership")
    public LoginResponseDto membership(@Valid @RequestBody MembershipRequestDto membershipRequestDto) {

        return memberService.membership(membershipRequestDto);
    }

    // 로그인
    @PostMapping("/login")
    public LoginResponseDto login(@Valid @RequestBody LoginRequestDto loginRequest) {

        return memberService.login(loginRequest);
    }

    @DeleteMapping("/withdraw")
    public WithdrawResponseDto withdraw(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return memberService.withdraw(customUserDetails.getId());

    }

}
