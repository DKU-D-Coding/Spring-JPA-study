package com.dku.springstudy.jwt;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JwtTokenValidatorTest {
    private final JwtTokenProvider tokenProvider = new JwtTokenProvider();
    private final JwtTokenValidator tokenValidator = new JwtTokenValidator();

    @DisplayName("토큰 검증 성공 테스트")
    @Test
    void successValidateToken() {
        String secretKey = "1234560ACB6F1AD6B6A6184A31E6B7E37DB3818CC36871E26235DD67DCFE4041492";
        long expiredMs = 30 * 60 * 1000L;
        String testToken = tokenProvider.createToken("test@naver.com", secretKey, expiredMs);

        assertThat(tokenValidator.validateToken(testToken, secretKey)).isTrue();
    }

    @DisplayName("토큰 검증 실패 - 생성/ 검증 시 사용하는 키가 다를 때")
    @Test
    void failValidateTokenByDifferentKey() {
        String secretKey1 = "1234560ACB6F1AD6B6A6184A31E6B7E37DB3818CC36871E26235DD67DCFE4041492";
        String secretKey2 = "9234560ACB6F1AD6B6A6184A31E6B7E37DB3818CC36871E26235DD67DCFE4041492";
        long expiredMs = 30 * 60 * 1000L;

        String testToken = tokenProvider.createToken("test@naver.com", secretKey1, expiredMs);

        assertThat(tokenValidator.validateToken(testToken, secretKey2)).isFalse();
    }

    @DisplayName("토큰 검증 실패 - 유효기간만료")
    @Test
    void failValidateTokenByExpired() {
        String secretKey = "1234560ACB6F1AD6B6A6184A31E6B7E37DB3818CC36871E26235DD67DCFE4041492";
        long expiredMs = 0L;

        String testToken = tokenProvider.createToken("test@naver.com", secretKey, expiredMs);

        assertThat(tokenValidator.validateToken(testToken, secretKey)).isFalse();
    }
}