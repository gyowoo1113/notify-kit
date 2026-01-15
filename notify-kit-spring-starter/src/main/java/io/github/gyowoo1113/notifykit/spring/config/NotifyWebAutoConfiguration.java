package io.github.gyowoo1113.notifykit.spring.config;

import io.github.gyowoo1113.notifykit.spring.infrastructure.delivery.advice.ExceptionControllerAdvice;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@AutoConfiguration
@ConditionalOnClass(RestControllerAdvice.class)
@Import(ExceptionControllerAdvice.class)
public class NotifyWebAutoConfiguration {

}
