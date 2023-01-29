package com.dku.springstudy.service;

import java.util.List;
import java.util.Optional;

import com.dku.springstudy.domain.Member;
import com.dku.springstudy.repository.MemberRepository;
import com.dku.springstudy.repository.MemoryMemberRepository;

public class MemberService {
    
    private final MemberRepository memberRepository = new MemoryMemberRepository();

    /* 회원 가입 */
    public Long join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
            .ifPresent(m -> {
                throw new IllegalThreadStateException("이미 존재하는 회원입니다.");
            });
    }

    /* 전체 회원 조회 */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
