package com.project.carrot.config;

import com.project.carrot.MyInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration //설정 파일이고 Bean을 등록할 것이다.
@RequiredArgsConstructor
public class InterceptorConfig implements WebMvcConfigurer {
    private final MyInterceptor myInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(myInterceptor)
                .addPathPatterns("/**"); //localhost:8080 의 이하 경로
    }
}
