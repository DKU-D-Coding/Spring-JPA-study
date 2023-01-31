package com.dku.springstudy.Member.service;


import com.dku.springstudy.Member.dto.LoginRequestDto;
import com.dku.springstudy.Member.dto.LoginResponseDto;
import com.dku.springstudy.Member.dto.SignupRequestDto;
import com.dku.springstudy.Member.dto.TokenResponse;
import com.dku.springstudy.Member.entity.Member;
import com.dku.springstudy.Member.entity.MemberROLE;
import com.dku.springstudy.Member.repository.MemberRepository;
import com.dku.springstudy.auth.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final AuthenticationManager authenticationManager;
    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;

    public void signup(SignupRequestDto request) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(request.getPassword());
        Member member = Member.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(encodedPassword)
                .phoneNumber(request.getPhoneNumber())
                .nickname(request.getNickname())
                .memberRole(MemberROLE.USER)
                .build();

        memberRepository.save(member);
    }


    public LoginResponseDto login(LoginRequestDto loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        TokenResponse createToken = createTokenReturn(loginRequest);
        Long tokenExpireTime = jwtProvider.getTokenExpireTime(createToken.getAccessToken());
        return new LoginResponseDto(
                createToken.getAccessToken(),
                tokenExpireTime);
    }

    private TokenResponse createTokenReturn(LoginRequestDto loginRequest) {

        String accessToken = jwtProvider.createAccessToken(loginRequest.getEmail());

        return new TokenResponse(accessToken);
    }
}
