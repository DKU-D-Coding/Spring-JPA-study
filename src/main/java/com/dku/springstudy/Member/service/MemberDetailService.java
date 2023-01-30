package com.dku.springstudy.Member.service;

import com.dku.springstudy.Member.entity.Member;
import com.dku.springstudy.Member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberDetailService implements UserDetailsService{

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        if (email.isBlank()) {
//            throw new EmailEmptyException();
        }

        Optional<Member> optionalUser = memberRepository.findByEmail(email);
        Member member = optionalUser.orElseThrow();

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        return new org.springframework.security.core.userdetails.User(member.getEmail(), member.getPassword(), authorities);
    }
}
