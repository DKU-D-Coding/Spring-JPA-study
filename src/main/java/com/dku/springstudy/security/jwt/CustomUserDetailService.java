package com.dku.springstudy.security.jwt;

import com.dku.springstudy.domain.Member;
import com.dku.springstudy.exception.CustomException;
import com.dku.springstudy.exception.ErrorCode;
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
    public UserDetails loadUserByUsername(String username) throws CustomException {
        Member member = memberRepository.findByName(username)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND_ERROR));
        return new CustomUserDetails(member);
    }
}
