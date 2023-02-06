package com.dku.springstudy.service;

import com.dku.springstudy.model.Member;
import com.dku.springstudy.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
// jpa를 통한 모든 데이터 변경은 트랜잭션 안에서 실행!
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final boolean LOGIN_SUCCESS = true;
    private final boolean LOGIN_FAIL = false;

    public String join(Member newMember) {
        validate(newMember);
        memberRepository.save(newMember);
        return newMember.getEmail();
    }

    private void validate(Member newMember) {
        String newMemberEmail = newMember.getEmail();
        if (findOne(newMemberEmail).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다");
        }
    }

    public boolean login(String email, String rawPassword) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("이메일을 다시 확인해주세요"));

        if (isMatchedPassword(member, rawPassword)) {
            return LOGIN_SUCCESS;
        }
        return LOGIN_FAIL;
    }

    private boolean isMatchedPassword(Member member, String rawPassword) {
        // 스트링의 equals메서드로 하면 안됨. encode결과값이 그때그때 달라서리..
        return passwordEncoder.matches(rawPassword, member.getPassword());
    }

    public Optional<Member> findOne(String email) {
        return memberRepository.findByEmail(email);
    }
}
