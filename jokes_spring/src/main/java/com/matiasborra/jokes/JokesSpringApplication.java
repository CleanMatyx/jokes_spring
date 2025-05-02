package com.matiasborra.jokes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class JokesSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(JokesSpringApplication.class, args);
	}

}
