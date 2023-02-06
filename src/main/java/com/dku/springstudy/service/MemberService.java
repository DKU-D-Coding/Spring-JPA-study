package com.dku.springstudy.service;

import com.dku.springstudy.model.Member;
import com.dku.springstudy.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
// jpa를 통한 모든 데이터 변경은 트랜잭션 안에서 실행!
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;

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

    public String login(String email, String password) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("이메일을 다시 확인해주세요"));

        if (isMatchedPassword(member, password)) {
            return "Login Success";
        }
        return "Login Fail";
    }

    private boolean isMatchedPassword(Member member, String password) {
        return member.getPassword().equals(password);
    }

    public Optional<Member> findOne(String email) {
        return memberRepository.findByEmail(email);
    }
}
