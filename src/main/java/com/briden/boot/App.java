package com.briden.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.briden")
public class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

}
