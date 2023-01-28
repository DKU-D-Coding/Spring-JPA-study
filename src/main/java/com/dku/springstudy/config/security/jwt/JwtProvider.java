package com.dku.springstudy.config.security.jwt;

import com.dku.springstudy.config.security.RedisDao;
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
import java.time.Duration;
import java.util.Base64;
import java.util.Date;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class JwtProvider {
    private final RedisDao redisDao;
    private final ObjectMapper objectMapper;

    @Value("${jwt.key}")
    private String key;

    @Value("${jwt.live.atk}")
    private Long atkLive;

    @Value("${jwt.live.rtk}")
    private Long rtkLive;

    @PostConstruct
    protected void init() {
        key = Base64.getEncoder().encodeToString(key.getBytes());
    }

    public TokenResponse reissueAtk(UserResponse userResponse) throws JsonProcessingException {
        String rtkInRedis = redisDao.getValues(userResponse.getEmail());
        if (Objects.isNull(rtkInRedis)) throw new IllegalStateException("인증 정보가 만료되었습니다.");
        Subject atkSubject = Subject.atk(
                userResponse.getUserId(),
                userResponse.getEmail(),
                userResponse.getNickname(),
                userResponse.getRole());
        String atk = createToken(atkSubject, atkLive);
        return new TokenResponse(atk, null);
    }
    public TokenResponse createTokensByLogin(UserResponse userResponse) throws JsonProcessingException {
        Subject atkSubject = Subject.atk(
                userResponse.getUserId(),
                userResponse.getEmail(),
                userResponse.getNickname(),
                userResponse.getRole());
        Subject rtkSubject = Subject.rtk(
                userResponse.getUserId(),
                userResponse.getEmail(),
                userResponse.getNickname(),
                userResponse.getRole());
        String atk = createToken(atkSubject, atkLive);
        String rtk = createToken(rtkSubject, rtkLive);
        redisDao.setValues(userResponse.getEmail(), rtk, Duration.ofMillis(rtkLive));
        return new TokenResponse(atk, rtk);
    }

    private String createToken(Subject subject, Long tokenLive) throws JsonProcessingException {
        String subjectStr = objectMapper.writeValueAsString(subject);
        Claims claims = Jwts.claims().setSubject(subjectStr);
        Date date = new Date();
        System.out.println(subject.getType());
        return Jwts.builder()
                .setSubject(claims.getId())
                .claim("userId", subject.getUserId())
                .claim("email",subject.getEmail())
                .claim("role",subject.getRole().name())
                .claim("type",subject.getType())
                .setIssuedAt(date)
                .setExpiration(new Date(date.getTime() + tokenLive))
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }

    public Subject getSubject(String atk) throws JsonProcessingException {
        String subjectStr = Jwts.parser().setSigningKey(key).parseClaimsJws(atk).getBody().getSubject();
        return objectMapper.readValue(subjectStr, Subject.class);
    }

    public Claims validateAndGetUserId(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();
        return claims;
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
