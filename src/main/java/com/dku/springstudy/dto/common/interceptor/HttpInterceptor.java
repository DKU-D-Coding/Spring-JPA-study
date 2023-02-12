//package com.dku.springstudy.dto.common.interceptor;
//
//import com.dku.springstudy.dto.common.SuccessResponse;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.util.ContentCachingResponseWrapper;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//@Slf4j
//@RequiredArgsConstructor
//@Component
//public class HttpInterceptor implements HandlerInterceptor {
//
//    private final ObjectMapper objectMapper;
//
//    // 사용자에게 response가 나가기 전에 처리
//    @Override
//    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception ex) throws Exception {
//
//        final ContentCachingResponseWrapper cachingResponse = (ContentCachingResponseWrapper) response; // 시큐리티 필터에서 넘어온 응답 값
//
//        if (!String.valueOf(response.getStatus()).startsWith("2")) {
//            return;
//        }
//
//        if ((cachingResponse.getContentType() != null) && (cachingResponse.getContentType().contains("application/json"))) { // JSON 응답 값만
//            if (cachingResponse.getContentAsByteArray().length != 0) {
//                String body = new String(cachingResponse.getContentAsByteArray());
//
//                Object data = objectMapper.readValue(body, Object.class);
//
//                SuccessResponse<Object> successResponseDto = new SuccessResponse<>(data);
//
//                String wrappedBody = objectMapper.writeValueAsString(successResponseDto);
//
//                cachingResponse.resetBuffer();
//
//                cachingResponse.getOutputStream().write(wrappedBody.getBytes(), 0, wrappedBody.getBytes().length);
//                log.info("SuccessResponse Body : ", wrappedBody);
//            }
//        }
//    }
//}
