package com.dku.springstudy.Member.controller;

import com.dku.springstudy.Member.dto.SignupRequestDto;
import com.dku.springstudy.Member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<String> signup(SignupRequestDto request) {
        memberService.signup(request);
        return ResponseEntity.ok().build();
    }
}
