package edu.uday.sie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@Configuration
@PropertySource("application.properties")
public class SieApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SieApiApplication.class, args);
	}

}
