package com.dku.springstudy.controller;

import com.dku.springstudy.domain.Member;
import com.dku.springstudy.dto.member.request.LoginRequestDto;
import com.dku.springstudy.dto.member.request.LogoutRequestDto;
import com.dku.springstudy.dto.member.request.MembershipRequestDto;
import com.dku.springstudy.dto.member.response.LoginResponseDto;
import com.dku.springstudy.dto.member.response.LogoutResponseDto;
import com.dku.springstudy.dto.member.response.MembershipResponseDto;
import com.dku.springstudy.dto.member.response.WithdrawResponseDto;
import com.dku.springstudy.security.CustomUserDetails;
import com.dku.springstudy.security.jwt.JwtProvider;
import com.dku.springstudy.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class MemberController {
    private final MemberService memberService;
    private final JwtProvider jwtProvider;

    // 회원가입
    @PostMapping("/member/membership")
    public MembershipResponseDto membership(@Valid @RequestBody MembershipRequestDto membershipRequestDto) {

        return memberService.membership(membershipRequestDto);
    }

    // 로그인
    @PostMapping("/member/login")
    public LoginResponseDto login(@Valid @RequestBody LoginRequestDto loginRequest) {
        return memberService.login(loginRequest);
    }

    @PostMapping("/member/logout")
    public LogoutResponseDto logout(@AuthenticationPrincipal CustomUserDetails customUserDetails, HttpServletRequest request) {
        Member member = customUserDetails.getMember();
        String accessToken = jwtProvider.resolveToken(request);
        return memberService.logout(member, accessToken);
    }


    @DeleteMapping("/member/withdraw")
    public WithdrawResponseDto withdraw(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Member member = customUserDetails.getMember();
        return memberService.withdraw(member);

    }

}
