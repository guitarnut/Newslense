package com;

import org.apache.log4j.PropertyConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableScheduling
public class Newslense {
	public static void main(String[] args) {
		SpringApplication.run(Newslense.class, args);
	}

	@PostConstruct
    private void setup() {
        PropertyConfigurator.configure("log4j.properties");
    }
}
