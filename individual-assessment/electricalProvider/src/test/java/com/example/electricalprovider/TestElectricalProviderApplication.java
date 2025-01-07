package com.example.electricalprovider;

import org.springframework.boot.SpringApplication;

public class TestElectricalProviderApplication {

	public static void main(String[] args) {
		SpringApplication.from(ElectricalProviderApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
