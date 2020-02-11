package com.meteo.sber;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class SberApplication {

	public static void main(String[] args) {
		SpringApplication.run(SberApplication.class, args);
	}

}
