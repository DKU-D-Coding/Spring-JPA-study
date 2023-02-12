package com.dku.springstudy.security.config;

import com.dku.springstudy.repository.UserRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SpringConfig {

    private final EntityManager entityManager;

    @Bean
    public UserRepository userRepository(){
        return new UserRepository(entityManager);
    }
}
