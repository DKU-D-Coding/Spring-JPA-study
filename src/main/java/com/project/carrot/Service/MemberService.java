package com.project.carrot.Service;

import com.project.carrot.domain.Member;
import com.project.carrot.domain.PostSaveDTO;
import com.project.carrot.repository.MemberRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@RequiredArgsConstructor
@Transactional//JPA를 통한 모든 데이터 변경은 트랜잭션 안에서 실행되어야만 한다.
public class MemberService implements UserDetailsService {
    /*
    회원가입, 로그인 기능 개발
     */
    private final MemberRepository memberRepository;

    public Member join(Member member){
        memberRepository.findByEmail(member.getUserEmail())
                .ifPresent(m->{throw new IllegalStateException("이미 존재하는 이메일입니다.");});

        return memberRepository.save(member);
    }

    //컨트롤러에서 역할 수행하게 되어 주석처리
    /*public Member Login(String email, String password){
        Member findMember = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("이메일이 일치하지 않습니다."));

        if(findMember.getUserPass() != password) throw new IllegalStateException("패스워드가 일치하지 않습니다.");

        return findMember;
    }*/

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return memberRepository.findByEmail(username)
                .orElseThrow(()->new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
    }
}
