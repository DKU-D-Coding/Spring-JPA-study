package com.dku.springstudy.service;

import com.dku.springstudy.domain.Member;
import com.dku.springstudy.dto.member.request.LoginRequestDto;
import com.dku.springstudy.dto.member.request.MembershipRequestDto;
import com.dku.springstudy.dto.member.response.LoginResponseDto;
import com.dku.springstudy.dto.member.response.LogoutResponseDto;
import com.dku.springstudy.dto.member.response.MembershipResponseDto;
import com.dku.springstudy.dto.member.response.WithdrawResponseDto;
import com.dku.springstudy.exception.CustomException;
import com.dku.springstudy.exception.ErrorCode;
import com.dku.springstudy.repository.MemberRepository;
import com.dku.springstudy.security.CustomUserDetails;
import com.dku.springstudy.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final RedisTemplate<String,String> redisTemplate;


    public MembershipResponseDto membership(MembershipRequestDto membershipRequestDto){
        //이메일로 중복 회원 방지 해야함
        Member member = Member.builder()
                .email(membershipRequestDto.getEmail())
                .name(membershipRequestDto.getName())
                .password(passwordEncoder.encode(membershipRequestDto.getPassword()))
                .build();

        memberRepository.save(member);

        String loginAccessToken  = jwtProvider.createAccessToken(member.getName());
        String loginRefreshToken = jwtProvider.createRefreshToken(member.getName());

        return new MembershipResponseDto(loginAccessToken, loginRefreshToken);
        //return new LoginResponseDto(loginAccessToken);
    }
    public WithdrawResponseDto withdraw(Member member){
        try{
            memberRepository.remove(member);
            return new WithdrawResponseDto(true);
        }catch (Exception e){
            return new WithdrawResponseDto(false,e.getMessage());
        }
    }

    public LoginResponseDto login(LoginRequestDto loginRequestDto){

        Member member = memberRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND_ERROR));

        if(passwordEncoder.matches(loginRequestDto.getPassword(), member.getPassword())) {

            String accessToken  = jwtProvider.createAccessToken(member.getName());
            String refreshToken = jwtProvider.createRefreshToken(member.getName());

            //redis에 RefreshToken 저장
            redisTemplate.opsForValue()
                    .set(member.getEmail(), refreshToken,
                            jwtProvider.getExpiration(refreshToken), TimeUnit.MILLISECONDS);

            return new LoginResponseDto(accessToken, refreshToken);
            //return new LoginResponseDto(loginAccessToken);
        } else {
            throw new CustomException(ErrorCode.USER_PASSWORD_INCORRECT_ERROR); //custom exception 만들기
        }

    }

    public LogoutResponseDto logout(Member member, String accessToken){

        try {
            //엑세스 토큰 남은 유효시간
            Long expiration = jwtProvider.getExpiration(accessToken);
            //해당 Access Token 유효시간 가지고 와서 BlackList 로 저장하기
            redisTemplate.opsForValue().set(accessToken, "logout", expiration, TimeUnit.MILLISECONDS);

            //리프레쉬 토큰 삭제
            redisTemplate.delete(member.getEmail());

            return new LogoutResponseDto(true);
        }catch (Exception e){
            return  new LogoutResponseDto(false, e.getMessage());
        }

    }

}
