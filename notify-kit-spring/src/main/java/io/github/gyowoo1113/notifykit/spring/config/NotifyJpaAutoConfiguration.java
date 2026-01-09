package io.github.gyowoo1113.notifykit.spring.config;

import io.github.gyowoo1113.notifykit.core.service.port.NotificationRepository;
import io.github.gyowoo1113.notifykit.spring.infrastructure.persistence.NotificationJPARepository;
import io.github.gyowoo1113.notifykit.spring.infrastructure.persistence.NotificationRepositoryAdapter;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@ConditionalOnClass(org.springframework.data.jpa.repository.JpaRepository.class)
public class NotifyJpaAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(NotificationRepository.class)
    public NotificationRepository notificationRepository(NotificationJPARepository jpaRepository) {
        return new NotificationRepositoryAdapter(jpaRepository);
    }
}