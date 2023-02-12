package com.dku.springstudy.security.jwt;

// 토큰을 생성하고 검증하는 클래스
// 해당 컴포넌트는 필터클래스에서 사전 검증을 거침

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtProvider {

    private String secretKey = "kangho";
    private final UserDetailsService userDetailsService;

    private Key getSigninKey(String secretKey) {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(Claims claims, long expiredDuration) {
        Date createdTime = new Date();
        Date ExpiredTime = new Date(createdTime.getTime() + expiredDuration);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(createdTime)
                .setExpiration(ExpiredTime)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String createAccessToken(String userPK) {
        Claims claims = Jwts.claims();
        claims.setSubject(userPK);
        long accessTokenValidMilliSecond = 60 * 60 * 1000L;

        return createToken(claims, accessTokenValidMilliSecond);
    }

    public String createRefreshToken(String userPK) {
        Claims claims = Jwts.claims();
        claims.setSubject(userPK);
        long refreshTokenValidMilliSecond = 24 * 60 * 60 * 1000L;

        return createToken(claims, refreshTokenValidMilliSecond);
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUserPk(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("X-AUTH-TOKEN");
    }

    public boolean validateToken(String token){
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return !claimsJws.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public Long getExpiration(String accessToken){

        Date expiration = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(accessToken)
                .getBody().getExpiration();
        long now = new Date().getTime();
        return expiration.getTime() - now;
    }

}
