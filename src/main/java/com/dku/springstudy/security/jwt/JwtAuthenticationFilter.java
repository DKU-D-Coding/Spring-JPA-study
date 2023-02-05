package com.dku.springstudy.security.jwt;

import com.dku.springstudy.dto.common.ErrorResponse;
import com.dku.springstudy.exception.CustomException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;

    public static final String AUTHORIZATION_HEADER = "Authorization";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            // SecurityFilter에서 넘어오는 Request와 Response 캐싱
            ContentCachingRequestWrapper wrappingRequest = new ContentCachingRequestWrapper((HttpServletRequest) request);
            ContentCachingResponseWrapper wrappingResponse = new ContentCachingResponseWrapper((HttpServletResponse) response);

            String token = resolveToken((HttpServletRequest) request);

            if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) { // 토큰이 유효하다면
                Authentication authentication = jwtTokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication); // 사용자 정보를 SecurityContext에 저장
            } else {
                log.debug("유효한 JWT 토큰이 없습니다, uri: {}", ((HttpServletRequest) request).getRequestURI());
            }

            chain.doFilter(wrappingRequest , wrappingResponse);
            wrappingResponse.copyBodyToResponse(); // 캐싱된 응답값을 덮어쓴다.
        } catch (CustomException e) {
            HttpServletResponse errorResponse = (HttpServletResponse) response;

            // header 작성
            errorResponse.setStatus(e.getErrorCode().getHttpstatus().value());
            errorResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);

            // exception 응답값 생성
            HttpStatus status = e.getErrorCode().getHttpstatus();
            ResponseEntity<ErrorResponse> errorEntity = ResponseEntity
                                                                .status(status)
                                                                .body(new ErrorResponse(status.value(), e.getErrorCode().getMessage()));

            ObjectMapper objectMapper = new ObjectMapper();
            String exceptionMessage = objectMapper.writeValueAsString(errorEntity);

            errorResponse.getWriter().write(exceptionMessage);
            log.info("ErrorResponse Body : ", errorResponse);
        }
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER); // Header에서 token을 가져온다.

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // Bearer를 제외시킨 값
        }

        return null;
    }
}
