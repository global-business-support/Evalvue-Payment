package com.subscription;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(value = "com.*")
public class EvalvuePaymentServiceApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(EvalvuePaymentServiceApiApplication.class, args);
	}

}
