package com.project.carrot.controller;

import com.project.carrot.domain.Member;
import com.project.carrot.Service.MemberService;
import com.project.carrot.repository.MemberRepository;
import com.project.carrot.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Email;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;


@Slf4j //로깅할때 쓰는 어노테이션
@RestController
@RequiredArgsConstructor
public class JWTController {

    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;


    final String EMAIL = "aabbcc@gmail.com";
    final String PASS = "th1234";
    final String TEL="010-1111-1111";
    final String USERNAME="Yun";
    final String NICKNAME = "침착맨";
    final String ADDRESS= "Incheon";

    //final KafkaProperties.Admin ADMIN = Admin.일반회원; 권한설정 필요할 경우 설정

    /* 테스트용 유저 생성
    Member user = Member.builder()
            .UserEmail(EMAIL)
            .UserPass(PASS)
            .NickName(NICKNAME)
            //권한 설정이 필요할 경우 enum 써서 .admin(ADMIN)
            .UserTel(TEL)
            .UserName(USERNAME)
            .Address(ADDRESS)
            .roles(Collections.singletonList("ROLE_USER")) // 최초 가입시 USER 로 설정
            .build();

    */
    @PostMapping(value = "/join")
    public String joinUser() throws Exception{
        log.info("회원가입 시도");
        memberService.join(user);

        return user.toString();
    }

    @PostMapping(value = "/login")
    public String login(@RequestBody Map<String, String> user){
        Member member=memberRepository.findByEmail(user.get("email"))
                .orElseThrow(()->new IllegalArgumentException("가입되지 않은 이메일입니다."));

        Member member1=memberRepository.findByPass(user.get("password"))
                .orElseThrow(()->new IllegalArgumentException("비밀번호가 일치하지 않습니다."));

        return jwtTokenProvider.createToken(member.getUsername(), member.getRoles());
    }
}
