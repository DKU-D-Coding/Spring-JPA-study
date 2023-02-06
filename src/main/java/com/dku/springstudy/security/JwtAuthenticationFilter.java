package com.dku.springstudy.security;

import com.dku.springstudy.exception.KarrotException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;
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
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        try{
            String token = getTokenFromHeader(requestWrapper);
            String path = requestWrapper.getServletPath();
            if(path.startsWith("/member/reissue")){
                filterChain.doFilter(requestWrapper,responseWrapper);
            } else {
                if(token != null && jwtTokenProvider.validateToken(token)){
                    Authentication authentication = jwtTokenProvider.getAuthentication(token);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
                filterChain.doFilter(requestWrapper, responseWrapper);
                responseWrapper.copyBodyToResponse();
            }
        } catch (ExpiredJwtException e){
            log.info("Expired JWT token", e);
        } catch (KarrotException e){
            response.setStatus(e.getHttpStatus().value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);

            ResponseEntity<KarrotException> exception = ResponseEntity.status(e.getHttpStatus()).body(new KarrotException(e.getHttpStatus(), e.getCode(), e.getMessage()));
            ObjectMapper objectMapper = new ObjectMapper();
            String exceptionMessage = objectMapper.writeValueAsString(exception);
            response.getWriter().write(exceptionMessage);
        }
    }

    private String getTokenFromHeader(ContentCachingRequestWrapper request) {
//        HttpServletRequest req = request;
        String bearerToken = request.getHeader("Authorization");
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")){
            return bearerToken.substring(7);
        }
        return null;
    }
}
