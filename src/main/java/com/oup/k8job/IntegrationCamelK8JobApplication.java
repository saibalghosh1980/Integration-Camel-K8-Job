package com.oup.k8job;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class IntegrationCamelK8JobApplication {

	public static void main(String[] args) {
		SpringApplication.run(IntegrationCamelK8JobApplication.class, args);
	}

}
