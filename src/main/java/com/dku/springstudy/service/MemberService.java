package com.dku.springstudy.service;

import com.dku.springstudy.domain.Member;
import com.dku.springstudy.domain.token.RefreshToken;
import com.dku.springstudy.exception.KarrotException;
import com.dku.springstudy.repository.jpa.MemberRepository;
import com.dku.springstudy.repository.redis.RefreshTokenRepository;
import com.dku.springstudy.security.JwtTokenProvider;
import com.dku.springstudy.dto.TokenDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public Long join(Member member){
        memberRepository.save(member);
        return member.getId();
    }

    @Transactional
    public TokenDto login(String email, String password){
        // email/password

        // email, password기반으로 Authentication 객체 생성
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);

        // 실제 검증
        // authenticate() 메서드 실행 시, CustomUserDetailService의 loadByUsername() 메서드 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 인증 정보를 기반으로 Token 생성
        return jwtTokenProvider.generateToken(authentication);
    }

    public TokenDto reissue(TokenDto token){
//        if(!jwtTokenProvider.validateToken(token.getRefreshToken())){
//            return ResponseEntity.badRequest().body("유효하지 않은 Refresh Token 입니다.");
//        }
        log.info("authentication");
        Authentication authentication = jwtTokenProvider.getAuthentication(token.getAccessToken());


        // redis에서 refresh Token 가져오기
        RefreshToken refreshToken = refreshTokenRepository.findById(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Refresh Token이 존재하지 않습니다."));
        if(!token.getRefreshToken().equals(refreshToken.getRefreshToken())){
            throw new RuntimeException("토큰 정보 유효성 검증 실패");
        }

        TokenDto reissuedTokenDto = jwtTokenProvider.generateToken(authentication);
        RefreshToken reissuedRefreshToken = new RefreshToken(authentication.getName(), reissuedTokenDto.getRefreshToken());
        refreshTokenRepository.save(reissuedRefreshToken);

        return reissuedTokenDto;
    }

    public Member getCurrentLoginMember(String email){
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new KarrotException(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(), "존재하지 않은 사용자 입니다."));
    }

}
