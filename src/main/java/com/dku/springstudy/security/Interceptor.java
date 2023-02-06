package com.dku.springstudy.security;

import com.dku.springstudy.dto.common.ErrorResponseDto;
import com.dku.springstudy.dto.common.SuccessResponseDto;
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
            Exception ex
    ) throws Exception {
        //wrapping 된 Response 가 넘어 올 것이다.
        final ContentCachingResponseWrapper cachingResponse = (ContentCachingResponseWrapper) response;
        //200번대 응답이 아닌 경우 Interceptor 를 거치지 않는다.

        //Json 형식의 응답값만 수정 - @ResponseBody를 통해 Object 반환은 JSON으로 넘어옴.
        if (cachingResponse.getContentType() != null && (cachingResponse.getContentType().contains("application/json"))) {

            if (cachingResponse.getContentAsByteArray().length != 0) {

                String body = new String(cachingResponse.getContentAsByteArray());
                Object data = objectMapper.readValue(body, Object.class);

                if (body.contains("BAD_REQUEST") || !String.valueOf(response.getStatus()).startsWith("2")) {

                    ErrorResponseDto<Object> errorResponseDto = new ErrorResponseDto<>(data);
                    //String 변환 -> Byte로 쓰기 위함.
                    String wrappedBody = objectMapper.writeValueAsString(errorResponseDto);

                    //flush 버퍼에 있는 데이터를 처리함.
                    cachingResponse.resetBuffer();

                    //덮어쓰기
                    cachingResponse.getOutputStream().write(wrappedBody.getBytes(), 0, wrappedBody.getBytes().length);

                }else{

                    //ResponseEntity 생성
                    SuccessResponseDto<Object> successResponseDto = new SuccessResponseDto<>(data);

                    //String 변환 -> Byte로 쓰기 위함.
                    String wrappedBody = objectMapper.writeValueAsString(successResponseDto);

                    //flush 버퍼에 있는 데이터를 처리함.
                    cachingResponse.resetBuffer();

                    //덮어쓰기
                    cachingResponse.getOutputStream().write(wrappedBody.getBytes(), 0, wrappedBody.getBytes().length);

                }

            }

        }
    }


}
