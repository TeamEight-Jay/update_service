package com.recommendation.update_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class UpdateServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UpdateServiceApplication.class, args);
	}

}

