//package com.dku.springstudy.config;
//
//import com.dku.springstudy.dto.common.interceptor.HttpInterceptor;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//@RequiredArgsConstructor
//public class InterceptorConfig implements WebMvcConfigurer {
//
//    private final HttpInterceptor httpInterceptor;
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(httpInterceptor) // 만든 인터셉터 등록
//                .addPathPatterns("/**");
//    }
//}
