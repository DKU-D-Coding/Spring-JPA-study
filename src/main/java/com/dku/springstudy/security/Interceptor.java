package com.dku.springstudy.security;

import com.dku.springstudy.dto.common.ErrorResponseDTO;
import com.dku.springstudy.dto.common.SuccessResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.ContentCachingResponseWrapper;

@Slf4j
@RequiredArgsConstructor
@Component
public class Interceptor implements HandlerInterceptor {
    private final ObjectMapper objectMapper;

    @Override
    public void afterCompletion(
            HttpServletRequest request,
            HttpServletResponse response,
            Object object,
            Exception ex) throws Exception {
        final ContentCachingResponseWrapper cachingResponse = (ContentCachingResponseWrapper) response;

        if (cachingResponse.getContentType() != null
                && (cachingResponse.getContentType().contains("application/json"))) {

            if (cachingResponse.getContentAsByteArray().length != 0) {

                String body = new String(cachingResponse.getContentAsByteArray());
                Object data = objectMapper.readValue(body, Object.class);

                if (body.contains("BAD_REQUEST") || !String.valueOf(response.getStatus()).startsWith("2")) {
                    ErrorResponseDTO<Object> errorResponseDto = new ErrorResponseDTO<>(data);
                    String wrappedBody = objectMapper.writeValueAsString(errorResponseDto);
                    cachingResponse.resetBuffer();
                    cachingResponse.getOutputStream().write(wrappedBody.getBytes(), 0, wrappedBody.getBytes().length);
                } else {
                    SuccessResponseDTO<Object> successResponseDto = new SuccessResponseDTO<>(data);
                    String wrappedBody = objectMapper.writeValueAsString(successResponseDto);
                    cachingResponse.resetBuffer();
                    cachingResponse.getOutputStream().write(wrappedBody.getBytes(), 0, wrappedBody.getBytes().length);
                }
            }
        }
    }
}
