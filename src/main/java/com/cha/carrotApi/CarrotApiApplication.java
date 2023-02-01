package com.cha.carrotApi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class CarrotApiApplication {
	public static void main(String[] args) {
		SpringApplication.run(CarrotApiApplication.class, args);
	}
}
