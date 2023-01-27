package com.dku.springstudy.config.security.jwt;

import com.dku.springstudy.dto.TokenResponse;
import com.dku.springstudy.dto.UserResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    private final ObjectMapper objectMapper;

    @Value("${jwt.key}")
    private String key;

    @Value("${jwt.live.atk}")
    private Long atkLive;

    @PostConstruct
    protected void init() {
        key = Base64.getEncoder().encodeToString(key.getBytes());
    }

    public TokenResponse createTokensByLogin(UserResponse userResponse) throws JsonProcessingException {
        Subject atkSubject = Subject.atk(
                userResponse.getUserId(),
                userResponse.getEmail(),
                userResponse.getNickname(),
                userResponse.getRole());
        String atk = createToken(atkSubject, atkLive);
        return new TokenResponse(atk, null);
    }

    private String createToken(Subject subject, Long tokenLive) throws JsonProcessingException {
        String subjectStr = objectMapper.writeValueAsString(subject);
        Claims claims = Jwts.claims().setSubject(subjectStr);
        Date date = new Date();
        return Jwts.builder()
                .setSubject(claims.getId())
                .claim("userId", subject.getUserId())
                .claim("role",subject.getRole().name())
                .setIssuedAt(date)
                .setExpiration(new Date(date.getTime() + tokenLive))
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }


    public Claims validateAndGetUserId(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();
        return claims; // 사용자의 아이디 리턴
    }

    public boolean isValidToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(token)
                    .getBody();
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
