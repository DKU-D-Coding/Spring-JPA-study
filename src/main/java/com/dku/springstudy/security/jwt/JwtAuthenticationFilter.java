package com.dku.springstudy.security.jwt;

import com.dku.springstudy.dto.common.ExceptionDto;
import com.dku.springstudy.exception.CustomException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;

@RequiredArgsConstructor
@Component
@Slf4j
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtProvider jwtProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            //SecurityFilter에서 넘어오는 Request & Response를 래핑(캐싱)
            ContentCachingRequestWrapper wrappingRequest = new ContentCachingRequestWrapper((HttpServletRequest) request);
            ContentCachingResponseWrapper wrappingResponse = new ContentCachingResponseWrapper((HttpServletResponse) response);

            String token = jwtProvider.resolveToken((HttpServletRequest) request);
            // 유효한 토큰인지 확인합니다.
            if (token != null && jwtProvider.validateToken(token)) {
                // 토큰이 유효하면 토큰으로부터 유저 정보를 받아옵니다.
                Authentication authentication = jwtProvider.getAuthentication(token);
                // SecurityContext 에 Authentication 객체를 저장합니다.
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            chain.doFilter(wrappingRequest, wrappingResponse);
            wrappingResponse.copyBodyToResponse(); //캐싱된 응답값을 덮어씀

        } catch (CustomException e) {
            HttpServletResponse errorResponse = (HttpServletResponse) response;
            //header 작성
            errorResponse.setStatus(e.getErrorCode().getStatus());
            errorResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
            //exception 응답값 생성
            ExceptionDto exceptionDto = new ExceptionDto(e);

            ObjectMapper objectMapper = new ObjectMapper();
            String exceptionMessage = objectMapper.writeValueAsString(exceptionDto);

            errorResponse.getWriter().write(exceptionMessage);
        }

    }

}
