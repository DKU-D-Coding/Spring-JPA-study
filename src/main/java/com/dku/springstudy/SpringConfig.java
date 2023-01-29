package com.dku.springstudy;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import static com.dku.springstudy.SpringConfig.BASE_PACKAGE;

@Configuration
@EnableJpaRepositories(basePackages = {BASE_PACKAGE})
public class SpringConfig {
    static final String BASE_PACKAGE = "com.dku.springstudy.repository.jpa";
}
