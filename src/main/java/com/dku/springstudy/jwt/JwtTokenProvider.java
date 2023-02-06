package com.dku.springstudy.jwt;

import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    private final UserDetailsService userDetailsService;

    public String createToken(String userEmail, String jwtSecretKey, long expiredMs) {
        // claims : jwt에서 내가 원하는 걸 담는 공간. payload라고 보면 됨. 일종의 map
        Claims claims = Jwts.claims();
        claims.put("email", userEmail);

        return Jwts.builder()
                .setClaims(claims) // 위에서 만들어둔 claims 넣기
                .setIssuedAt(new Date(System.currentTimeMillis())) // 현재 시간 넣기
                .setExpiration(new Date(System.currentTimeMillis() + expiredMs)) // 종료시간 넣기
                .signWith(SignatureAlgorithm.HS256, jwtSecretKey) // 서명하기
                .compact();
    }

    public boolean validateToken(String token, String jwtSecretKey) {
        try {
            // 토큰 복호화
            Claims claims = Jwts.parser() // parser 생성
                    .setSigningKey(jwtSecretKey) //  JWS 디지털 서명을 확인하는 데 쓰일 키를 세팅
                    .parseClaimsJws(token)
                    .getBody();
            return true;
        } catch (SignatureException e) {
            // throw new IllegalArgumentException("토큰을 만들 때 쓰인 키가 아닙니다");
            return false;
        } catch (ExpiredJwtException e) {
            return false;
        }
    }

    public String getTokenFromHeader(HttpServletRequest request) {
        return request.getHeader("X-AUTH-TOKEN");
    }

    public String getUserEmailFromToken(String token, String jwtSecretKey) {
        // 토큰 복호화
        Claims claims = Jwts.parser() // parser 생성
                .setSigningKey(jwtSecretKey) //  JWS 디지털 서명을 확인하는 데 쓰일 키를 세팅
                .parseClaimsJws(token)
                .getBody();
        String userEmail = (String)claims.get("email"); // object형태로 저장돼있어서..문자열로 변환해야 함
        return userEmail;
    }

    public Authentication getAuthentication(String token, String jwtSecretKey) {
        String email = getUserEmailFromToken(token, jwtSecretKey);
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
}
