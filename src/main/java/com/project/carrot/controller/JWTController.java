package com.project.carrot.controller;

import com.project.carrot.MyInterceptor;
import com.project.carrot.domain.Member;
import com.project.carrot.Service.MemberService;
import com.project.carrot.domain.PostSaveDTO;
import com.project.carrot.repository.MemberRepository;
import com.project.carrot.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Email;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;


@Slf4j //로깅할때 쓰는 어노테이션
@RestController
@RequiredArgsConstructor
public class JWTController {

    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;


    @PostMapping(value = "/join")
    public Member joinUser(@RequestBody PostSaveDTO postSaveDTO) {
        log.info("회원가입 시도");
        Member member = postSaveDTO.toEntity();
        return memberService.join(member);
    }

    @PostMapping(value = "/login")
    public String login(@RequestBody Map<String, String> user) {
        Member member=memberRepository.findByEmail(user.get("EMAIL"))
                .orElseThrow(()->new IllegalArgumentException("가입되지 않은 이메일입니다."));

        log.info(user.get("PASS"));
        log.info(member.getPassword());

        if(!user.get("PASS").equals(member.getPassword()))
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");

        return jwtTokenProvider.createToken(member.getUserEmail(), member.getRoles());
    }
    @PostMapping(value = "/findId")
    public String findId(@RequestBody Map<String,String> userTel) {
        Member member = memberRepository.findByUserTel(userTel.get("userTel"))
                .orElseThrow(() -> new IllegalArgumentException("계정이 존재하지 않습니다."));

        return member.getUserEmail();
    }

    @PostMapping(value = "/findPassword")//전화번호와 이메일을 입력
    public String findPassword(@RequestBody Map<String,String> userInfo){
        Member member1=memberRepository.findByUserTel(userInfo.get("userTel"))
                .orElseThrow(()->new RuntimeException("계정이 존재하지 않습니다."));

        Member member2=memberRepository.findByEmail(userInfo.get("Email"))
                .orElseThrow(()->new RuntimeException("계정이 존재하지 않습니다."));

        if(!member1.equals(member2))
            throw new RuntimeException("계정이 존재하지 않습니다.");

        return member1.getPassword();
    }
}