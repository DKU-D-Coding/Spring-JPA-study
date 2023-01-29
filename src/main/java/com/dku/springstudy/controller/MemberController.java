package com.dku.springstudy.controller;

import com.dku.springstudy.domain.Member;
import com.dku.springstudy.dto.LoginDto;
import com.dku.springstudy.dto.TokenDto;
import com.dku.springstudy.enums.Role;
import com.dku.springstudy.repository.jpa.MemberRepository;
import com.dku.springstudy.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberRepository memberRepository;

    private final MemberService memberService;

    @PostMapping("/login")
    public TokenDto login(@RequestBody LoginDto loginDto){
        log.info("login request={}", loginDto);
        return memberService.login(loginDto.getEmail(), loginDto.getPassword());
    }
    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(@RequestBody TokenDto tokenDto){
        log.info("Reissue={}", tokenDto);
        return memberService.reissue(tokenDto);
    }

    @GetMapping("/user")
    public ResponseEntity<?> accessUser(HttpServletRequest request){
        return ResponseEntity.ok(request);
    }

    @GetMapping("/admin")
    public String accessAdmin(){
        return "ok";
    }

    @PostConstruct
    public void init(){
        Member member = Member.createMember("test@naver.com", "1234", "lee", "01010", "nickname", Role.USER);
        memberRepository.save(member);
    }


}
