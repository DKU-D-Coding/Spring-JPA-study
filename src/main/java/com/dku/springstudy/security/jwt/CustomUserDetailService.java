package com.dku.springstudy.security.jwt;

import com.dku.springstudy.domain.Member;
import com.dku.springstudy.dto.CustomUserDetails;
import com.dku.springstudy.exception.UserNotFoundException;
import com.dku.springstudy.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UserNotFoundException {
        Member member = memberRepository.findByName(username)
                .orElseThrow(UserNotFoundException::new);
        return new CustomUserDetails(member);
    }
}
