package io.github.gyowoo1113.notifykit.spring.config;

import io.github.gyowoo1113.notifykit.core.service.NotificationService;
import io.github.gyowoo1113.notifykit.core.service.port.EventIdGenerator;
import io.github.gyowoo1113.notifykit.core.service.port.NotificationRepository;
import io.github.gyowoo1113.notifykit.spring.application.NotificationFacade;
import io.github.gyowoo1113.notifykit.spring.config.properties.OutboxProperties;
import io.github.gyowoo1113.notifykit.spring.infrastructure.delivery.common.AtomicEventIdGenerator;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@EnableConfigurationProperties(OutboxProperties.class)
public class NotifyCoreAutoConfiguration {

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
}