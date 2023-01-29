package com.dku.springstudy.service;

import com.dku.springstudy.domain.Member;
import com.dku.springstudy.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Long membership(Member member){
        memberRepository.save(member);
        return member.getId();
    }

    public Optional<Member> findOne(Long id){
        return memberRepository.findById(id);
    }

    public boolean checkPassword(Member member,String password){
        return member.getPassword().equals(password);
    }


}
