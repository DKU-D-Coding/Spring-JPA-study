package com.cha.carrotApi.service;

import com.cha.carrotApi.domain.Member;
import com.cha.carrotApi.jwt_security.JwtTokenProvider;
import com.cha.carrotApi.repository.MemberRepository;
import com.cha.carrotApi.repository.MemberSignUpRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService{

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public Long signUp(MemberSignUpRequestDto requestDto) throws Exception {

        if(memberRepository.findByEmail(requestDto.getEmail()).isPresent()){
            throw new Exception("이미 존재하는 이메일입니다.");
        }

        Member member = memberRepository.save(requestDto.toEntity());
        member.encodePassword(passwordEncoder);

        member.addUserAuthority();
        return member.getId();
    }


    public String login(Map<String, String> members) {
        Member member = memberRepository.findByEmail(members.get("email"))
                .orElseThrow(() -> new IllegalStateException("가입되지 않은 Email 입니다."));
        String password = members.get("password");
        if (!passwordEncoder.matches(password,member.getPassword())) {
            throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
        }

        List<String> roles = new ArrayList<>();
        roles.add(member.getRole().name());

        return jwtTokenProvider.createToken(member.getEmail(), roles);
    }
}
