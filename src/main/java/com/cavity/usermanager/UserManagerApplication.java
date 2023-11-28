package com.cavity.usermanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UserManagerApplication {

	public static void main(String[] args) {
		String databaseFile = System.getenv("DATABASE_FILE");

		if (databaseFile != null && !databaseFile.isEmpty()) {
			System.setProperty("spring.datasource.url", "jdbc:sqlite:" + databaseFile);
		}

		SpringApplication.run(UserManagerApplication.class, args);
	}
}