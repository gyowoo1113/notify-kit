package io.github.gyowoo1113.notifykit.spring.config;

import io.github.gyowoo1113.notifykit.spring.config.condition.ConditionalOnOutboxPipelineEnabled;
import io.github.gyowoo1113.notifykit.spring.infrastructure.delivery.outbox.OutboxScheduler;
import io.github.gyowoo1113.notifykit.spring.infrastructure.delivery.outbox.OutboxWorker;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

@AutoConfiguration(after = NotifyOutboxAutoConfiguration.class)
@ConditionalOnOutboxPipelineEnabled
@ConditionalOnBean(OutboxWorker.class)
public class NotifyOutboxSchedulerAutoConfiguration {

    @Bean
    @ConditionalOnProperty(prefix="notify.outbox.scheduler", name="enabled", havingValue="true")
    OutboxScheduler outboxScheduler(OutboxWorker worker) {
        return new OutboxScheduler(worker);
    }
}
