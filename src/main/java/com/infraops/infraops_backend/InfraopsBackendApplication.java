package com.infraops.infraops_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableMongoAuditing
@EnableScheduling
public class InfraopsBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(InfraopsBackendApplication.class, args);
	}

}
