package com.dku.springstudy.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenValidator {
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
}
