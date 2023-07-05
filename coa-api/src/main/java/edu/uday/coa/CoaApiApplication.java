package edu.uday.coa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@Configuration
@PropertySource("application.properties")
public class CoaApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoaApiApplication.class, args);
	}

}
