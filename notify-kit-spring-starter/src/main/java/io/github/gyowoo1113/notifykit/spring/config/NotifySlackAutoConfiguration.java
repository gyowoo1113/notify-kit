package io.github.gyowoo1113.notifykit.spring.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.gyowoo1113.notifykit.core.service.port.OutboxSender;
import io.github.gyowoo1113.notifykit.spring.config.properties.NotifySlackProperties;
import io.github.gyowoo1113.notifykit.spring.infrastructure.delivery.channel.slack.SlackClient;
import io.github.gyowoo1113.notifykit.spring.infrastructure.delivery.channel.slack.SlackOutboxSender;
import io.github.gyowoo1113.notifykit.spring.infrastructure.delivery.channel.slack.port.SlackTargetResolver;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@AutoConfiguration(before = NotifyOutboxAutoConfiguration.class)
@EnableConfigurationProperties(NotifySlackProperties.class)
@ConditionalOnProperty(prefix = "notify.slack", name = "enabled", havingValue = "true")
public class NotifySlackAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public SlackClient slackClient(NotifySlackProperties properties) {
        return new SlackClient(properties.webhookUrl());
    }

    @Bean
    @ConditionalOnMissingBean
    public SlackTargetResolver slackTargetResolver() {
        return message -> "default";
    }

    @Bean
    @ConditionalOnProperty(prefix = "notify.outbox", name = "sender", havingValue = "slack")
    @ConditionalOnMissingBean(OutboxSender.class)
    public OutboxSender slackOutboxSender(
            SlackClient slackClient,
            SlackTargetResolver targetResolver,
            ObjectMapper objectMapper
    ) {
        return new SlackOutboxSender(slackClient, targetResolver, objectMapper);
    }
}
