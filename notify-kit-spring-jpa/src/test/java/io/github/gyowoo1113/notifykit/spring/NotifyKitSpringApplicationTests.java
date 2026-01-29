package io.github.gyowoo1113.notifykit.spring;

import io.github.gyowoo1113.notifykit.spring.jpa.config.NotifyJpaAutoConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

class NotifyKitSpringApplicationTests {

	private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
			.withConfiguration(AutoConfigurations.of(NotifyJpaAutoConfiguration.class));
	@Test
	void contextLoads() {
	}

}
