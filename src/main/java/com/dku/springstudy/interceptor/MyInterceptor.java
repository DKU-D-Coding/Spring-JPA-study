package com.dku.springstudy.interceptor;

import com.dku.springstudy.util.SuccessResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.ContentCachingResponseWrapper;

@RequiredArgsConstructor
@Component
public class MyInterceptor implements HandlerInterceptor {
    private final String SUCCESS_PREFIX = "2";
    private final String JSON_CONTENT_TYPE = "application/json";
    // response를 object로 매핑해야 됨
    private final ObjectMapper objectMapper;

    @Override
    public void afterCompletion(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler, Exception ex
    ) throws Exception {
        // 필터에서 wrapping됐던 response가 산 넘고 강 건너 결국 이 메서드로 넘어올 것임
        final ContentCachingResponseWrapper cachingResponse = (ContentCachingResponseWrapper) response;
        // 200번대 응답이 아니면 인터셉터를 거치지 않도록
        if (!isSuccessStatus(response.getStatus())) {
            return;
        }
        if (cachingResponse.getContentType() != null
                && cachingResponse.getContentType().contains(JSON_CONTENT_TYPE)
                && cachingResponse.getContentAsByteArray().length != 0) {
            // String 변환
            String body = new String(cachingResponse.getContentAsByteArray());
            // Object 형식으로 변환 (Response에 꽂아주기 위함)
            Object data = objectMapper.readValue(body, Object.class);
            // 컨트롤러가 뱉는 모든 DTO들을 형식에 상관없이 ResponseDTO에 담는 것!
            SuccessResponseDTO<Object> objectSuccessResponseDTO = new SuccessResponseDTO<>(data);
            // String 변환
            String wrappedBody = objectMapper.writeValueAsString(objectSuccessResponseDTO);
            // 비우고 (원래는 여기에 SuccessResponseDTO가 아닌 다른 놈, 즉 통일된 형식을 갖추기 전의 무언가가 있었을 거니까)
            cachingResponse.resetBuffer();
            // 웅답값 교체
            cachingResponse.getOutputStream().write(wrappedBody.getBytes(), 0, wrappedBody.getBytes().length);
        }

    }

    private boolean isSuccessStatus(int status) {
        return String.valueOf(status).startsWith(SUCCESS_PREFIX);
    }
}