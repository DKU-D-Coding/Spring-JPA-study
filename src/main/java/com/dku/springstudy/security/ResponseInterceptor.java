package com.dku.springstudy.security;

import com.dku.springstudy.dto.ResponseEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Slf4j
@Component
@RequiredArgsConstructor
public class ResponseInterceptor implements HandlerInterceptor {
    private final ObjectMapper objectMapper;

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ContentCachingResponseWrapper res = (ContentCachingResponseWrapper) response;

        if(!request.getRequestURI().contains("swagger")){
            if(!request.getRequestURI().contains("v2")){
                String contentString = new String(res.getContentAsByteArray());
                Object readValue = objectMapper.readValue(contentString, Object.class);

                ResponseEntity<Object> objectResponseEntity = new ResponseEntity<>(readValue, HttpStatus.OK);
                String wrappedBody = objectMapper.writeValueAsString(objectResponseEntity);
                res.resetBuffer();
                res.getOutputStream().write(wrappedBody.getBytes(), 0, wrappedBody.getBytes().length);
            }
        }

        res.copyBodyToResponse();

    }
}
