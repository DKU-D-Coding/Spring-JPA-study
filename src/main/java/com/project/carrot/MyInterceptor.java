package com.project.carrot;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.carrot.domain.PostSaveDTO;
import com.project.carrot.domain.SuccessResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j //로깅에 사용하는 어노테이션
@RequiredArgsConstructor
@Component
public class MyInterceptor implements HandlerInterceptor {
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
        if (!String.valueOf(response.getStatus()).startsWith("2")) {
            return;
        }
        //Json 형식의 응답값만 수정 - @ResponseBody를 통해 Object 반환은 JSON으로 넘어옴.
        if (cachingResponse.getContentType() != null && (cachingResponse.getContentType().contains("application/json"))) {
            if (cachingResponse.getContentAsByteArray().length != 0) {
                //String 변환 ex){"key":"value"}
                String body = new String(cachingResponse.getContentAsByteArray());

                //Object형식으로 변환 -> Response에 꽂아주기 위함.
                Object data = objectMapper.readValue(body, Object.class);

                //ResponseEntity 생성
                SuccessResponseDTO<Object> objectResponseDTO = new SuccessResponseDTO<>(data);

                //String 변환 -> Byte로 쓰기 위함.
                String wrappedBody = objectMapper.writeValueAsString(objectResponseDTO);

                //flush 버퍼에 있는 데이터를 처리함.
                cachingResponse.resetBuffer();

                //덮어쓰기
                cachingResponse.getOutputStream().write(wrappedBody.getBytes(), 0, wrappedBody.getBytes().length);
                log.info("Response Body : {}", wrappedBody);
            }
        }
    }
}
