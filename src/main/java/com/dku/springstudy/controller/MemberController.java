package com.dku.springstudy.controller;

import com.dku.springstudy.domain.Member;
import com.dku.springstudy.dto.JoinDto;
import com.dku.springstudy.dto.LoginDto;
import com.dku.springstudy.dto.TokenDto;
import com.dku.springstudy.enums.Role;
import com.dku.springstudy.repository.jpa.MemberRepository;
import com.dku.springstudy.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberRepository memberRepository;

    private final MemberService memberService;

    @PostMapping("/join")
    public JoinDto join(@RequestBody JoinDto joinDto){
        log.info("Join Request={}", joinDto);
        Member joinMember = Member.createMember(
                joinDto.getEmail(),
                joinDto.getPassword(),
                joinDto.getName(),
                joinDto.getPhone(),
                joinDto.getNickname(),
                Role.USER
        );
        memberService.join(joinMember);
        return joinDto;
    }

    @PostMapping("/login")
    public TokenDto login(@RequestBody LoginDto loginDto){
        log.info("login request={}", loginDto);
        return memberService.login(loginDto.getEmail(), loginDto.getPassword());
    }
    @PostMapping("/reissue")
    public TokenDto reissue(@RequestBody TokenDto tokenDto){
        log.info("Reissue={}", tokenDto);
        return memberService.reissue(tokenDto);
    }

}
