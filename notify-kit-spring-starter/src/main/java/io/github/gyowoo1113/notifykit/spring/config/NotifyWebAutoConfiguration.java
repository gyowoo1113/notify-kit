package io.github.gyowoo1113.notifykit.spring.config;

import io.github.gyowoo1113.notifykit.core.service.port.RecentEventStore;
import io.github.gyowoo1113.notifykit.core.service.port.noop.RecentNoopEventStore;
import io.github.gyowoo1113.notifykit.spring.infrastructure.delivery.advice.ExceptionControllerAdvice;
import io.github.gyowoo1113.notifykit.core.service.port.noop.NoopNotificationEventPublisher;
import io.github.gyowoo1113.notifykit.spring.infrastructure.delivery.event.NotificationCreatedEventListener;
import io.github.gyowoo1113.notifykit.core.service.port.NotificationEventPublisher;
import io.github.gyowoo1113.notifykit.spring.infrastructure.delivery.sse.*;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@AutoConfiguration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@ConditionalOnClass(name = "org.springframework.web.servlet.DispatcherServlet")
@Import(ExceptionControllerAdvice.class)
public class NotifyWebAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public AtomicEventIdGenerator atomicEventIdGenerator() {
        return new AtomicEventIdGenerator();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(name = "notify.sse.enabled", havingValue = "false", matchIfMissing = true)
    public RecentEventStore recentNoopEventStore() {
        return new RecentNoopEventStore();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(name = "notify.sse.enabled", havingValue = "true")
    public RecentEventStore recentSseEventStore() {
        return new RecentSseEventStore();
    }

    @Bean
    @ConditionalOnMissingBean
    public SseEmitterRegistry sseEmitterRegistry(){
        return new SseEmitterRegistry();
    }

    @Bean
    @ConditionalOnProperty(name = "notify.sse.enabled", havingValue = "false", matchIfMissing = true)
    @ConditionalOnMissingBean(NotificationEventPublisher.class)
    public NotificationEventPublisher noopNotificationEventPublisher() {
        return new NoopNotificationEventPublisher();
    }

    @Bean
    @ConditionalOnProperty(name = "notify.sse.enabled", havingValue = "true")
    public NotificationEventPublisher sseNotificationEventPublisher(SseEmitterRegistry registry,
                                                                    RecentEventStore recentEventStore,
                                                                    AtomicEventIdGenerator eventIdGenerator) {
        return new SseNotificationEventPublisher(registry, recentEventStore, eventIdGenerator);
    }

    @Bean
    @ConditionalOnMissingBean
    public NotificationCreatedEventListener notificationCreatedEventListener(NotificationEventPublisher publisher){
        return new NotificationCreatedEventListener(publisher);
    }

    @Bean
    @ConditionalOnProperty(name = "notify.sse.enabled", havingValue = "true")
    public SseSubscribeController sseSubscribeController(SseEmitterRegistry registry, RecentEventStore recentEventStore) {
        return new SseSubscribeController(registry, recentEventStore);
    }
}