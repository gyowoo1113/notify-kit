package io.github.gyowoo1113.notifykit.spring.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "notify.slack")
public record NotifySlackProperties(
        boolean enabled,
        String webhookUrl
) {
}
