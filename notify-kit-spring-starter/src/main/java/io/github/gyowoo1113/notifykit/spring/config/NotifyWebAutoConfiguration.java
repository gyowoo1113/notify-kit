package io.github.gyowoo1113.notifykit.spring.config;

import io.github.gyowoo1113.notifykit.spring.infrastructure.delivery.advice.ExceptionControllerAdvice;
import io.github.gyowoo1113.notifykit.spring.infrastructure.delivery.event.NoopNotificationEventPublisher;
import io.github.gyowoo1113.notifykit.spring.infrastructure.delivery.event.NotificationCreatedEventListener;
import io.github.gyowoo1113.notifykit.spring.infrastructure.delivery.event.NotificationEventPublisher;
import io.github.gyowoo1113.notifykit.spring.infrastructure.delivery.sse.SseEmitterRegistry;
import io.github.gyowoo1113.notifykit.spring.infrastructure.delivery.sse.SseNotificationEventPublisher;
import io.github.gyowoo1113.notifykit.spring.infrastructure.delivery.sse.SseSubscribeController;
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
    public NotificationEventPublisher sseNotificationEventPublisher(SseEmitterRegistry registry) {
        return new SseNotificationEventPublisher(registry);
    }

    @Bean
    @ConditionalOnMissingBean
    public NotificationCreatedEventListener notificationCreatedEventListener(NotificationEventPublisher publisher){
        return new NotificationCreatedEventListener(publisher);
    }

    @Bean
    @ConditionalOnProperty(name = "notify.sse.enabled", havingValue = "true")
    public SseSubscribeController sseSubscribeController(SseEmitterRegistry registry) {
        return new SseSubscribeController(registry);
    }
}