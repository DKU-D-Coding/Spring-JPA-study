package com.dku.springstudy.service;

import com.dku.springstudy.domain.Member;
import com.dku.springstudy.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private MemberRepository memberRepository;

    public Long join(Member member){
        memberRepository.save(member);
        return member.getId();
    }

}
