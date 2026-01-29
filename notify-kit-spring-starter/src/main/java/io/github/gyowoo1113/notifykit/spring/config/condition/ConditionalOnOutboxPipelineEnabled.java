package io.github.gyowoo1113.notifykit.spring.config.condition;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ConditionalOnProperty(prefix = "notify.outbox", name = "enabled", havingValue = "true")
public @interface ConditionalOnOutboxPipelineEnabled {
}
