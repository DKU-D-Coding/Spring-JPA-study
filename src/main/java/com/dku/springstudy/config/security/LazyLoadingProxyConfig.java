package com.dku.springstudy.config.security;

import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LazyLoadingProxyConfig {
    @Bean
    Hibernate5Module hibernate5Module() {
        return new Hibernate5Module();
    }
}
