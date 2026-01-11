package io.github.gyowoo1113.notify_kit_example;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class NotifyKitExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotifyKitExampleApplication.class, args);
	}

	@Bean
	CommandLineRunner beanCount(org.springframework.context.ApplicationContext ctx) {
		return args -> {
		};
	}
}

