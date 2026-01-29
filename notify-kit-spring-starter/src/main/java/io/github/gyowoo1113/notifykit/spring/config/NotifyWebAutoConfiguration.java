package io.github.gyowoo1113.notifykit.spring.config;

import io.github.gyowoo1113.notifykit.core.service.port.OutboxSender;
import io.github.gyowoo1113.notifykit.core.service.port.RecentEventStore;
import io.github.gyowoo1113.notifykit.spring.infrastructure.delivery.advice.ExceptionControllerAdvice;
import io.github.gyowoo1113.notifykit.spring.infrastructure.delivery.sse.*;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@AutoConfiguration(before = NotifyOutboxAutoConfiguration.class)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@ConditionalOnClass(name = "org.springframework.web.servlet.DispatcherServlet")
@Import(ExceptionControllerAdvice.class)
public class NotifyWebAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix="notify.outbox", name="sender", havingValue="sse")
    public RecentEventStore recentSseEventStore() {
        return new RecentSseEventStore();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix="notify.outbox", name="sender", havingValue="sse")
    public SseEmitterRegistry sseEmitterRegistry(){
        return new SseEmitterRegistry();
    }

    @Bean
    @ConditionalOnProperty(prefix="notify.outbox", name="sender", havingValue="sse")
    @ConditionalOnMissingBean(OutboxSender.class)
    public OutboxSender sseOutboxSender(SseEmitterRegistry registry, RecentEventStore recentEventStore) {
        return new SseOutboxSender(registry, recentEventStore);
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix="notify.outbox", name="sender", havingValue="sse")
    public SseSubscribeController sseSubscribeController(SseEmitterRegistry registry, RecentEventStore recentEventStore) {
        return new SseSubscribeController(registry, recentEventStore);
    }
}