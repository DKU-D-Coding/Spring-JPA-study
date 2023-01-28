package com.project.carrot.domain.Service;

import com.project.carrot.domain.Member;
import com.project.carrot.repository.MemberRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@RequiredArgsConstructor
@Transactional//JPA를 통한 모든 데이터 변경은 트랜잭션 안에서 실행되어야만 한다.
public class MemberService {
    /*
    회원가입, 로그인 기능 개발
     */
    private final MemberRepository memberRepository;

    public Long join(Member member){
        memberRepository.findByEmail(member.getUserEmail())
                .ifPresent(m->{throw new IllegalStateException("이미 존재하는 이메일입니다.");});
        memberRepository.save(member);

        return member.getUserId();
    }

    public Long Login(String email, String password){
        Member findMember = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("이메일이 일치하지 않습니다."));

        if(findMember.getUserPass() != password) throw new IllegalStateException("패스워드가 일치하지 않습니다.");

        return findMember.getUserId();
    }

}
