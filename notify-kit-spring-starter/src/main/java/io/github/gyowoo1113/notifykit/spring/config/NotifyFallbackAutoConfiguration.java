package io.github.gyowoo1113.notifykit.spring.config;

import io.github.gyowoo1113.notifykit.core.service.NotificationService;
import io.github.gyowoo1113.notifykit.core.service.port.NotificationCreatedHandler;
import io.github.gyowoo1113.notifykit.core.service.port.noop.NoopNotificationCreatedHandler;
import io.github.gyowoo1113.notifykit.spring.application.NotificationFacade;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

@AutoConfiguration(after = NotifyOutboxAutoConfiguration.class)
public class NotifyFallbackAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(NotificationCreatedHandler.class)
    public NotificationCreatedHandler noopNotificationCreatedHandler() {
        return new NoopNotificationCreatedHandler();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean({NotificationService.class, NotificationCreatedHandler.class})
    public NotificationFacade notificationFacade(NotificationService notificationService, NotificationCreatedHandler notificationCreatedHandler) {
        return new NotificationFacade(notificationService, notificationCreatedHandler);
    }
}
