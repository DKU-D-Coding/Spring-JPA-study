package com.dku.springstudy.config;

import com.dku.springstudy.jwt.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider tokenProvider;
    @Value("${jwt.secret}")
    private String jwtSecretKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 파라미터로 받은 request, response 객체들이 한 번 읽으면 없어지는 애들이라 한 번 감싼 애들을 만들어준다. 일종의 복제
        ContentCachingRequestWrapper wrappingRequest = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper wrappingResponse = new ContentCachingResponseWrapper(response);

        String token = tokenProvider.getTokenFromHeader(request);
        if (token != null && tokenProvider.validateToken(token, jwtSecretKey)) {
            Authentication authentication = tokenProvider.getAuthentication(token, jwtSecretKey);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(wrappingRequest, wrappingResponse);

        // 필터엔 복제한 애들 넘기기. 인터셉터도 래핑된 애들을 파라미터로 받음
        filterChain.doFilter(request, wrappingResponse);
        // 마지막에 클라이언트에게 response나갈 때 wrapping한 내용을 써줌(복제본 내용을 원본에 쓴다!)
        wrappingResponse.copyBodyToResponse();
    }
}
