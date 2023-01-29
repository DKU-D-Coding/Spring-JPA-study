package com.dku.springstudy.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {
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
}
