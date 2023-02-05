package com.dku.springstudy.controller;

import com.dku.springstudy.domain.Member;
import com.dku.springstudy.dto.common.SuccessResponseDto;
import com.dku.springstudy.dto.member.request.LoginRequestDto;
import com.dku.springstudy.dto.member.request.MembershipRequestDto;
import com.dku.springstudy.dto.member.request.WithdrawRequestDto;
import com.dku.springstudy.dto.member.response.LoginResponseDto;
import com.dku.springstudy.dto.member.response.WithdrawResponseDto;
import com.dku.springstudy.repository.MemberRepository;
import com.dku.springstudy.security.jwt.JwtProvider;
import com.dku.springstudy.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class MemberController {
    private final MemberService memberService;

    // 회원가입
    @PostMapping("/membership")
    public ResponseEntity<SuccessResponseDto<LoginResponseDto>> membership(@Valid @RequestBody MembershipRequestDto membershipRequestDto) {
        LoginResponseDto loginResponseDto = memberService.membership(membershipRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new SuccessResponseDto<>(loginResponseDto));
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<SuccessResponseDto<LoginResponseDto>> login(@Valid @RequestBody LoginRequestDto loginRequest) {
        LoginResponseDto loginResponseDto = memberService.login(loginRequest);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new SuccessResponseDto<>(loginResponseDto));

    }

    @PostMapping("/withdraw")
    public ResponseEntity<SuccessResponseDto<WithdrawResponseDto>> withdraw(@Valid @RequestBody WithdrawRequestDto withdrawRequestDto) {
        Boolean success = memberService.withdraw(withdrawRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new SuccessResponseDto<>(new WithdrawResponseDto(success)));

    }

}
