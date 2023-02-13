package com.project.carrot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing //JpaAuditing을 활성화 해주는 기능
@SpringBootApplication
public class CarrotApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarrotApplication.class, args);
	}

}
