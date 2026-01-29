package io.github.gyowoo1113.notifykit.spring.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.gyowoo1113.notifykit.core.service.NotificationService;
import io.github.gyowoo1113.notifykit.core.service.OutboxProcessorService;
import io.github.gyowoo1113.notifykit.core.service.port.EventIdGenerator;
import io.github.gyowoo1113.notifykit.core.service.port.NotificationRepository;
import io.github.gyowoo1113.notifykit.core.service.port.OutboxRepository;
import io.github.gyowoo1113.notifykit.core.service.port.OutboxSender;
import io.github.gyowoo1113.notifykit.core.service.port.noop.NoopOutboxSender;
import io.github.gyowoo1113.notifykit.spring.application.NotificationFacade;
import io.github.gyowoo1113.notifykit.spring.infrastructure.delivery.common.AtomicEventIdGenerator;
import io.github.gyowoo1113.notifykit.spring.infrastructure.delivery.event.NotificationCreatedEventListener;
import io.github.gyowoo1113.notifykit.spring.infrastructure.delivery.outbox.OutboxWorker;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class NotifyAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(EventIdGenerator.class)
    public EventIdGenerator atomicEventIdGenerator() {
        return new AtomicEventIdGenerator();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean(NotificationRepository.class)
    public NotificationService notificationService(NotificationRepository notificationRepository) {
        return new NotificationService(notificationRepository);
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean(NotificationService.class)
    public NotificationFacade notificationFacade(NotificationService notificationService, ApplicationEventPublisher applicationEventPublisher) {
        return new NotificationFacade(notificationService, applicationEventPublisher);
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "notify.outbox", name = "enabled", havingValue = "true")
    @ConditionalOnBean({OutboxRepository.class, OutboxSender.class, ObjectMapper.class})
    public NotificationCreatedEventListener notificationCreatedEventListener(OutboxRepository outboxRepository, ObjectMapper objectMapper, EventIdGenerator generator){
        return new NotificationCreatedEventListener(outboxRepository,objectMapper,generator);
    }

    @Bean
    @ConditionalOnProperty(prefix="notify.outbox", name="sender", havingValue="noop")
    @ConditionalOnMissingBean(OutboxSender.class)
    public OutboxSender noopOutboxSender() {
        return new NoopOutboxSender();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "notify.outbox", name = "enabled", havingValue = "true")
    @ConditionalOnBean({OutboxRepository.class, OutboxSender.class})
    public OutboxProcessorService outboxProcessorService(OutboxRepository repository, OutboxSender sender){
        return new OutboxProcessorService(repository,sender);
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "notify.outbox", name = "enabled", havingValue = "true")
    @ConditionalOnBean(OutboxProcessorService.class)
    public OutboxWorker outboxWorker(OutboxProcessorService processorService){
        return new OutboxWorker(processorService);
    }
}