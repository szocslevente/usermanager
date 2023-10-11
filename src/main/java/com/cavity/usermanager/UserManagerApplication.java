package com.cavity.usermanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UserManagerApplication {

	public static void main(String[] args) {
		// Check if a command-line argument for the database file is provided
		if (args.length == 1) {
			// Use the first command-line argument as the database file location
			String databaseFile = args[0];
			// Set the database URL in the application properties
			System.setProperty("spring.datasource.url", "jdbc:sqlite:" + databaseFile);
		}
		SpringApplication.run(UserManagerApplication.class, args);
	}
}
