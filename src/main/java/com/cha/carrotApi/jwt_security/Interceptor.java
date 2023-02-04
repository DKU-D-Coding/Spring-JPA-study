package com.cha.carrotApi.jwt_security;

import com.cha.carrotApi.DTO.response.SuccessResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@Slf4j
@Component
public class Interceptor implements HandlerInterceptor {
    private final ObjectMapper objectMapper;

    @Override
    public void afterCompletion(
            HttpServletRequest request,
            HttpServletResponse response,
            Object object,
            Exception ex
    ) throws Exception {
        final ContentCachingResponseWrapper cachingResponse = (ContentCachingResponseWrapper) response;

        if (!String.valueOf(response.getStatus()).startsWith("2")) {
            return;
        }
        if (cachingResponse.getContentType() != null && (cachingResponse.getContentType().contains("application/json"))) {
            String body = new String(cachingResponse.getContentAsByteArray());

            Object data = objectMapper.readValue(body, Object.class);

            SuccessResponse<Object> objectResponseDto = new SuccessResponse<>(data);

            String wrappedBody = objectMapper.writeValueAsString(objectResponseDto);

            cachingResponse.resetBuffer();

            cachingResponse.getOutputStream().write(wrappedBody.getBytes(), 0, wrappedBody.getBytes().length);
            log.info("Response Body : {}", wrappedBody);
        }
    }

}
