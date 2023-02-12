package com.dku.springstudy.security.jwt;

import com.dku.springstudy.model.User;
import com.dku.springstudy.dto.common.ExceptionDTO;
import com.dku.springstudy.exception.CustomException;
import com.dku.springstudy.exception.ErrorCode;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Component
@Slf4j
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtProvider jwtProvider;
    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        try {
            ContentCachingRequestWrapper wrappingRequest = new ContentCachingRequestWrapper(
                    (HttpServletRequest) request);
            ContentCachingResponseWrapper wrappingResponse = new ContentCachingResponseWrapper(
                    (HttpServletResponse) response);

            String accessToken = jwtProvider.resolveToken((HttpServletRequest) request);

            if (accessToken != null) {
                if (jwtProvider.validateToken(accessToken)) {

                    String isLogout = redisTemplate.opsForValue().get(accessToken);
                    if (ObjectUtils.isEmpty(isLogout)) {
                        Authentication authentication = jwtProvider.getAuthentication(accessToken);
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    } else {
                        throw new CustomException(ErrorCode.INVALID_TOKEN_ERROR);
                    }

                } else {
                    throw new CustomException(ErrorCode.EXPIRED_TOKEN_ERROR);
                }
            }

            chain.doFilter(wrappingRequest, wrappingResponse);
            wrappingResponse.copyBodyToResponse();
        } catch (CustomException e) {
            HttpServletResponse errorResponse = (HttpServletResponse) response;
            errorResponse.setStatus(e.getErrorCode().getStatus());
            errorResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
            ExceptionDTO exceptionDto = new ExceptionDTO(e);
            ObjectMapper objectMapper = new ObjectMapper();
            String exceptionMessage = objectMapper.writeValueAsString(exceptionDto);
            errorResponse.getWriter().write(exceptionMessage);
        }
    }
}
