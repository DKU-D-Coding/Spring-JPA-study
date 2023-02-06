package com.dku.springstudy.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtProvider {

    private String secretKey = "secret";

    private final UserDetailsService userDetailsService;

    // 객체 초기화, secretKey를 Base64로 인코딩한다.
    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    // JWT 토큰 생성
    public String createToken(Claims claims, long expiredDuration) {
        Date now = new Date();
        // 토큰 유효시간 30분
        return Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + expiredDuration)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, secretKey)  // 사용할 암호화 알고리즘과
                // signature 에 들어갈 secret값 세팅
                .compact();
    }

    // 로그인 성공 시 access token 발급
    public String createLoginAccessToken(String userPK) {
        Claims claims = Jwts.claims();
        claims.setSubject(userPK);
        //claims.put("role", role);

        // 1시간
        long accessTokenValidMilliSecond = 60 * 60 * 1000L;
        return createToken(claims, accessTokenValidMilliSecond);
    }

    // 로그인 성공 시 refresh token 발급
    public String createLoginRefreshToken(String userPK) {
        Claims claims = Jwts.claims();
        claims.setSubject(userPK);

        //1일
        long refreshTokenValidMilliSecond = 24 * 60 * 60 * 1000L;
        return createToken(claims, refreshTokenValidMilliSecond);
    }


    // 토큰에서 Email 추출

    // JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }


    // 토큰에서 회원 정보 추출
    public String getUserPk(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    // Request의 Header에서 token 값을 가져옵니다. "X-AUTH-TOKEN" : "TOKEN값'
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("X-AUTH-TOKEN");
    }

    // 토큰의 유효성 + 만료일자 확인
    // 토큰 검증
    public boolean validateToken(String token){
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return !claimsJws.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    //JWT 토큰의 만료시간
    public Long getExpiration(String accessToken){

        Date expiration = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(accessToken)
                .getBody().getExpiration();

        long now = new Date().getTime();
        return expiration.getTime() - now;
    }

}
