package io.github.gyowoo1113.notifykit.spring.config;

import io.github.gyowoo1113.notifykit.core.service.NotificationService;
import io.github.gyowoo1113.notifykit.core.service.port.NotificationRepository;
import io.github.gyowoo1113.notifykit.spring.application.NotificationFacade;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class NotifyAutoConfiguration {

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
