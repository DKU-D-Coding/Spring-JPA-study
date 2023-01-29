package com.dku.springstudy.jwt;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JwtTokenAnalyzerTest {
    private final JwtTokenProvider tokenProvider = new JwtTokenProvider();
    private final JwtTokenAnalyzer tokenAnalyzer = new JwtTokenAnalyzer();

    @DisplayName("토큰에 저장된 이메일이 내가 적어준 이메일이 맞는지 테스트")
    @Test
    void getUserEmailFromToken() {
        String secretKey = "1234560ACB6F1AD6B6A6184A31E6B7E37DB3818CC36871E26235DD67DCFE4041492";
        String testEmail = "test@naver.com";
        long expiredMs = 30 * 60 * 1000L;
        String testToken = tokenProvider.createToken(testEmail, secretKey, expiredMs);

        assertThat(tokenAnalyzer.getUserEmailFromToken(testToken, secretKey)).isEqualTo(testEmail);
    }
}