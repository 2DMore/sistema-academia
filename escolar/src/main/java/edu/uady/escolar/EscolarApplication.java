package edu.uady.escolar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import lombok.extern.log4j.Log4j2;

@SpringBootApplication
@Configuration
@PropertySource("application.properties")
public class EscolarApplication {
	
    public static void main(String[] args) {
        SpringApplication.run(EscolarApplication.class, args);
    }
    
}
