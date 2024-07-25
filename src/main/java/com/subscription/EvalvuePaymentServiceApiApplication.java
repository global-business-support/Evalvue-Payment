package com.subscription;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan(value = "com.*")
@EnableScheduling
public class EvalvuePaymentServiceApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(EvalvuePaymentServiceApiApplication.class, args);
	}

}
