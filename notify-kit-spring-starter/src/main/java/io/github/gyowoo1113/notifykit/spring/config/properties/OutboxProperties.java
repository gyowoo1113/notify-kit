package io.github.gyowoo1113.notifykit.spring.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@Getter
@Setter
@ConfigurationProperties(prefix = "notify.outbox")
public class OutboxProperties {
    private int batchSize = 100;
    private int retryLimit = 10;
    private Duration retryDelay = Duration.ofSeconds(3);
}
