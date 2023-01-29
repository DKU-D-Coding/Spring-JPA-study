package com.dku.springstudy.service;

import com.dku.springstudy.domain.Member;
import com.dku.springstudy.repository.jpa.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return memberRepository.findByEmail(username) // username = email
                .map(this::createUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("해당 회원이 존재하지 않습니다."));
    }

    private UserDetails createUserDetails(Member member){
        return User.builder()
                .username(member.getEmail())
                .password(passwordEncoder.encode(member.getPassword()))
                .roles(member.getRole().toString())
                .build();
    }
}
