package com.projet9.diabetesassessmentservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients("com.projet9.diabetesassessmentservice")
public class DiabetesAssessmentServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(DiabetesAssessmentServiceApplication.class, args);
	}

}
