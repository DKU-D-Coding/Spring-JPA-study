package com.dku.springstudy.security.jwt;

import com.dku.springstudy.security.CustomUserDetailsService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final CustomUserDetailsService customUserDetailsService;

    @Value("${jwt.secret-key}")
    private String secretKey;

    private final Long accessTokenValidMilliSecond = 24 * 60 * 60 * 1000L; // 1일
    private final Long refreshTokenValidMilliSecond = 30 * 24 * 60 * 60 * 1000L; // 30일

    // secretKey 값을 BASE64로 decode해 key 변수에 할당
    private Key getSecretKey(String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // 토큰 발급
    public String createToken(Claims claims, long expiredDuration) {
        Date now = new Date();

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setClaims(claims)
                .signWith(getSecretKey(secretKey), SignatureAlgorithm.HS256)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expiredDuration))
                .compact();
    }

    // 로그인 성공 시 access token 발급
    public String createLoginAccessToken(String userEmail, String role) {
        Claims claims = Jwts.claims();
        claims.setSubject(userEmail);
        claims.put("role", role);

        return createToken(claims, accessTokenValidMilliSecond);
    }

    // 로그인 성공 시 refresh token 발급
    public String createLoginRefreshToken(String userEmail) {
        Claims claims = Jwts.claims();
        claims.setSubject(userEmail);

        return createToken(claims, refreshTokenValidMilliSecond);
    }

    // 토큰에서 인증 정보 추출
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(getEmail(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 토큰에서 Email 추출
    public String getEmail(String token) {
        return Jwts.parserBuilder().setSigningKey(getSecretKey(secretKey)).build().parseClaimsJws(token).getBody().getSubject();
    }

    // 토큰 검증
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(getSecretKey(secretKey)).build().parseClaimsJws(token);
            return !claimsJws.getBody().getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            log.info("만료된 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
        } catch (SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
        }
        return false;
    }
}
