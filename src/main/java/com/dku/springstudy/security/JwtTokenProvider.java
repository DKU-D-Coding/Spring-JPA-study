package com.dku.springstudy.security;

import com.dku.springstudy.domain.token.RefreshToken;
import com.dku.springstudy.dto.TokenDto;
import com.dku.springstudy.repository.redis.RefreshTokenRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtTokenProvider {
    private final Key key;

    private final RefreshTokenRepository refreshTokenRepository;

    @Autowired
    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey, RefreshTokenRepository refreshTokenRepository){
        byte[] bytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(bytes);
        this.refreshTokenRepository = refreshTokenRepository;
    }

    // authentication 객체를 기반으로 access token, refresh token 생성
    public TokenDto generateToken(Authentication authentication){
        // 권한 정보
        String authorities = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = new Date().getTime();

        // access token 생성
        Date accessTokenExpDate = new Date(now + 3600000); // 발급시간으로부터 1h
        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim("auth", authorities)
                .setExpiration(accessTokenExpDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        // refresh token 생성
        Date refreshTokenExpDate = new Date(now + 86400000);
        String refreshToken = Jwts.builder()
                .setExpiration(refreshTokenExpDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
        // refresh token 저장
        RefreshToken refToken = new RefreshToken(authentication.getName(), refreshToken);
        refreshTokenRepository.save(refToken);


        return TokenDto.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    // token을 복호화하여 토큰 정보 꺼내는 메서드
    // client로부터 받은 token 정보를 통해 Authentication 객체 생성
    public Authentication getAuthentication(String accessToken){
        Claims claims = getClaims(accessToken);

        if(claims.get("auth") == null){
            throw new RuntimeException("권한이 없는 토큰입니다.");
        }

        // claims에서 권한정보 가져오기
        List<SimpleGrantedAuthority> authorities = Arrays.stream(claims.get("auth").toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        // UserDetails 객체 만들어서 Authentication return
        UserDetails principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    // 토큰 유효성 검증
    public boolean validateToken(String token){
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty.", e);
        }
        return false;
    }

    private Claims getClaims(String accessToken) {
        try{
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch(ExpiredJwtException e){
            return e.getClaims();
        }
    }


}
