package com.grace.elior;

import org.springframework.boot.SpringApplication;

public class TestEliorApplication {

	public static void main(String[] args) {
		SpringApplication.from(EliorApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
