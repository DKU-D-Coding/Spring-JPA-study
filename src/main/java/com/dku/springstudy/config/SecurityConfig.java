package com.dku.springstudy.config;

import com.dku.springstudy.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity // 기본적인 웹 보안을 활성화하는 어노테이션
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtTokenProvider tokenProvider;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable() // CSRF 공격에 대한 방어를 해제
                .cors().and() // cors 허용
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 서버를 stateless하게 유지 즉 세션 X
                .and()
                .formLogin().disable() // 시큐리티가 기본 제공하는 로그인 화면 없앰. JWT는 로그인과정을 수동으로 클래스로 만들어야 하니까
                .httpBasic().disable() // 토큰 방식을 이용할 것이므로 보안에 취약한 HttpBasic은 꺼두기
                .authorizeRequests() // HttpServletRequest를 사용하는 요청들에 대한 접근제한을 설정하겠다
                .requestMatchers("/account/signup", "/account/login", "/").permitAll() // 요 세 놈에 대한 요청은 인증없이 접근 허용
                .anyRequest().authenticated() // 나머지에 대해선 인증을 받아야 한다.
                .and()
                // 여러 필터들 중 UsernamePassword필터 앞에 내가 만든 필터를 둔다. 이렇게 하면 커스텀 필터로 인가인증을 다룰 수 있음
                .addFilterBefore(new JwtAuthenticationFilter(tokenProvider),
                        UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}