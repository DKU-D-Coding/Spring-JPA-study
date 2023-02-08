package com.dku.springstudy.member.controller;

import com.dku.springstudy.member.dto.LoginRequestDto;
import com.dku.springstudy.member.dto.LoginResponseDto;
import com.dku.springstudy.member.dto.SignupRequestDto;
import com.dku.springstudy.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupRequestDto request) {
        memberService.signup(request);
        return ResponseEntity.ok().body("회원가입에 성공하셨습니다.");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto request) {
        LoginResponseDto member = memberService.login(request);
        return ResponseEntity.ok().body(member);
    }
}
