package me.mryakar.tp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestTestingPracticesApplication {

	public static void main(String[] args) {
		SpringApplication.from(Start::main).with(TestTestingPracticesApplication.class).run(args);
	}

}
