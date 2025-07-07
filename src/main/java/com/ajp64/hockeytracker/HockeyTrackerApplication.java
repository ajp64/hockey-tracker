package com.ajp64.hockeytracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing // Automatically populates annotated JPA auditing fields
public class HockeyTrackerApplication {

	public static void main(String[] args) {

		SpringApplication.run(HockeyTrackerApplication.class, args);
	}

}
