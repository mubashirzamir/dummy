package com.example.SmartCity;

import org.springframework.boot.SpringApplication;

public class TestSmartCityApplication {

	public static void main(String[] args) {
		SpringApplication.from(SmartCityApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
