package io.github.gyowoo1113.notifykit.spring.config;

import io.github.gyowoo1113.notifykit.core.service.port.OutboxSender;
import io.github.gyowoo1113.notifykit.core.service.port.RecentEventStore;
import io.github.gyowoo1113.notifykit.core.service.port.noop.NoopOutboxSender;
import io.github.gyowoo1113.notifykit.core.service.port.noop.RecentNoopEventStore;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

@AutoConfiguration(before = NotifyOutboxAutoConfiguration.class)
public class NotifyNoopAutoConfiguration {

    @Bean
    @ConditionalOnProperty(prefix="notify.outbox", name="sender", havingValue="noop", matchIfMissing = true)
    @ConditionalOnMissingBean(OutboxSender.class)
    public OutboxSender noopOutboxSender() {
        return new NoopOutboxSender();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix="notify.outbox", name="sender", havingValue="noop", matchIfMissing = true)
    public RecentEventStore recentNoopEventStore() {
        return new RecentNoopEventStore();
    }

}
