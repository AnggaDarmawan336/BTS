package com.code.BTS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class ToDoOn {

	public static void main(String[] args) {
		SpringApplication.run(ToDoOn.class, args);
	}

}
