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
        memberRepository.save(newMember);
        return newMember.getEmail();
    }

    public Optional<Member> findOne(String email) {
        return memberRepository.findByEmail(email);
    }
}
