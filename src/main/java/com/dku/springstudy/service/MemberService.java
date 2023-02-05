package com.dku.springstudy.service;

import com.dku.springstudy.domain.Member;
import com.dku.springstudy.dto.member.request.LoginRequestDto;
import com.dku.springstudy.dto.member.request.MembershipRequestDto;
import com.dku.springstudy.dto.member.request.WithdrawRequestDto;
import com.dku.springstudy.dto.member.response.LoginResponseDto;
import com.dku.springstudy.exception.CustomException;
import com.dku.springstudy.exception.ErrorCode;
import com.dku.springstudy.repository.MemberRepository;
import com.dku.springstudy.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public LoginResponseDto membership(MembershipRequestDto membershipRequestDto){
        //이메일로 중복 회원 방지 해야함
        Member member = Member.builder()
                .email(membershipRequestDto.getEmail())
                .name(membershipRequestDto.getName())
                .password(passwordEncoder.encode(membershipRequestDto.getPassword()))
                .build();

        memberRepository.save(member);

        String loginAccessToken  = jwtProvider.createLoginAccessToken(member.getName());
        String loginRefreshToken = jwtProvider.createLoginRefreshToken(member.getName());

        return new LoginResponseDto(loginAccessToken, loginRefreshToken);
    }

    public Boolean withdraw(WithdrawRequestDto withdrawRequestDto){
        //나중에 토큰 시간 지나면 재발급 하는거 처리 해야함

        return false;
    }

    public LoginResponseDto login(LoginRequestDto loginRequestDto){

        Member member = memberRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND_ERROR));

        if(passwordEncoder.matches(loginRequestDto.getPassword(), member.getPassword())) {
            String loginAccessToken  = jwtProvider.createLoginAccessToken(member.getName());
            String loginRefreshToken = jwtProvider.createLoginRefreshToken(member.getName());
            return new LoginResponseDto(loginAccessToken, loginRefreshToken);
        } else {
            throw new CustomException(ErrorCode.USER_PASSWORD_INCORRECT_ERROR); //custom exception 만들기
        }

    }

}
