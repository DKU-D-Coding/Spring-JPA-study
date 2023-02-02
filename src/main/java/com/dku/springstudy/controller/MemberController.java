package com.dku.springstudy.controller;

import com.dku.springstudy.domain.Member;
import com.dku.springstudy.repository.MemberRepository;
import com.dku.springstudy.security.jwt.JwtProvider;
import com.dku.springstudy.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class MemberController {

    private final JwtProvider jwtProvider;
    private final MemberService memberService;
    private final MemberRepository memberRepository;


    // 회원가입
    @PostMapping("/membership")
    public Long membership(@RequestBody Map<String, String> user) {
        return memberService.membership(Member.builder()
                .email(user.get("email"))
                .password(user.get("password"))
                .name(user.get("name"))
                //.roles(Collections.singletonList("ROLE_ADMIN")) // 최초 가입시 USER 로 설정
                .build());
    }

    // 로그인
    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> user) {
        /*Member member = memberRepository.findByEmail(user.get("email"))
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.BAD_REQUEST, "존재하지 않는 계정입니다.", new IllegalArgumentException()));
        if ( !user.get("password").equals(member.getPassword()) ){
            throw new  ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 비밀번호입니다.", new IllegalArgumentException());
        }

         */
        String token = jwtProvider.createToken(user.get("name"));
        Authentication authentication = jwtProvider.getAuthentication(token);
        System.out.println("authentication.getName() = " + authentication.getName());

        return token;
    }

    @PostMapping("/user/**")
    public String userResponse() {
        Map<String, String> result = new HashMap<>();
        result.put("result","user ok");
        return result.toString();
    }

    @PostMapping("/admin/**")
    public String adminResponse() {
        Map<String, String> result = new HashMap<>();
        result.put("result","admin ok");
        return result.toString();
    }
}
