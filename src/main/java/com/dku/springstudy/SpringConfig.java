package com.dku.springstudy;

import com.dku.springstudy.repository.MemberRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor // 생성자 생략 가능하게 해주는 듯?
public class SpringConfig {

    private final EntityManager em;

    @Bean
    public MemberRepository userRepository(){
        return new MemberRepository(em);
    }

}
