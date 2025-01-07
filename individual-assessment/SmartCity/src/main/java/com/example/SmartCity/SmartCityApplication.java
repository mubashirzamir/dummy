package com.example.SmartCity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SmartCityApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartCityApplication.class, args);
	}


}
