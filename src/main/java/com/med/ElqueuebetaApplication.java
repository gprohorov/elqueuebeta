package com.med;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ElqueuebetaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ElqueuebetaApplication.class, args);
	}
}
