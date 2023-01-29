package com.dku.springstudy.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenAnalyzer {
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
}
