package com.example.citizen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The main class for the Citizen service.
 */
@SpringBootApplication
public class CitizenApplication {

	/**
	 * The main method serves as the entry point for the Citizen microservice.
	 * It uses {@link SpringApplication#run(Class, String...)} to launch the Spring Boot application,
	 * initializing the application context and starting the embedded web server.
	 *
	 * @param args command-line arguments passed to the application at startup
	 */
	public static void main(String[] args) {
		SpringApplication.run(CitizenApplication.class, args);
	}

}
