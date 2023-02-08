package com.dku.springstudy.auth;


import com.dku.springstudy.auth.exception.TokenException;
import com.dku.springstudy.error.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {

        String token = jwtProvider.resolveToken(request);

        try {
            if (token != null && jwtProvider.validateJwtToken(token)) {
                Authentication authentication = jwtProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (TokenException e) {
            HttpServletResponse errorResponse = (HttpServletResponse) response;
            errorResponse.setStatus(e.getErrorCode().getHttpStatus());
            errorResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
            ResponseEntity<ErrorResponse> exceptionDto = ResponseEntity.status(e.getErrorCode().getHttpStatus()).body(ErrorResponse.from(e.getErrorCode()));

            ObjectMapper objectMapper = new ObjectMapper();
            String exceptionMessage = objectMapper.writeValueAsString(exceptionDto);

            errorResponse.getWriter().write(exceptionMessage);
        }
        chain.doFilter(request, response);
    }
}
