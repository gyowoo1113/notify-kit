package io.github.gyowoo1113.notifykit.spring.jpa.config;

import io.github.gyowoo1113.notifykit.core.service.port.NotificationRepository;
import io.github.gyowoo1113.notifykit.spring.config.NotifyAutoConfiguration;
import io.github.gyowoo1113.notifykit.spring.jpa.infrastructure.persistence.NotificationEntity;
import io.github.gyowoo1113.notifykit.spring.jpa.infrastructure.persistence.NotificationJPARepository;
import io.github.gyowoo1113.notifykit.spring.jpa.infrastructure.persistence.NotificationRepositoryAdapter;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@AutoConfiguration
@ConditionalOnClass(org.springframework.data.jpa.repository.JpaRepository.class)
@EnableJpaRepositories(basePackageClasses = NotificationJPARepository.class)
@EntityScan(basePackageClasses = NotificationEntity.class)
@AutoConfigureBefore(NotifyAutoConfiguration.class)
public class NotifyJpaAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(NotificationRepository.class)
    public NotificationRepository notificationRepository(NotificationJPARepository jpaRepository) {
        return new NotificationRepositoryAdapter(jpaRepository);
    }
}