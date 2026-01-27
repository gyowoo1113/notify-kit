package io.github.gyowoo1113.notifykit.spring.jpa.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.github.gyowoo1113.notifykit.core.service.port.NotificationRepository;
import io.github.gyowoo1113.notifykit.core.service.port.OutboxRepository;
import io.github.gyowoo1113.notifykit.spring.config.NotifyAutoConfiguration;
import io.github.gyowoo1113.notifykit.spring.jpa.infrastructure.persistence.notification.NotificationEntity;
import io.github.gyowoo1113.notifykit.spring.jpa.infrastructure.persistence.notification.NotificationJpaRepository;
import io.github.gyowoo1113.notifykit.spring.jpa.infrastructure.persistence.notification.NotificationRepositoryAdapter;
import io.github.gyowoo1113.notifykit.spring.jpa.infrastructure.persistence.outbox.OutboxJpaRepository;
import io.github.gyowoo1113.notifykit.spring.jpa.infrastructure.persistence.outbox.OutboxMessageEntity;
import io.github.gyowoo1113.notifykit.spring.jpa.infrastructure.persistence.outbox.OutboxRepositoryAdapter;
import jakarta.persistence.EntityManager;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@AutoConfiguration
@ConditionalOnClass({JpaRepository.class, JPAQueryFactory.class})
@EnableJpaRepositories(basePackageClasses = {
        NotificationJpaRepository.class,
        OutboxJpaRepository.class
})
@EntityScan(basePackageClasses = {
        NotificationEntity.class,
        OutboxMessageEntity.class
})
@AutoConfigureBefore(NotifyAutoConfiguration.class)
public class NotifyJpaAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public JPAQueryFactory jpaQueryFactory(EntityManager em) {
        return new JPAQueryFactory(em);
    }

    @Bean
    @ConditionalOnMissingBean(NotificationRepository.class)
    public NotificationRepository notificationRepository(NotificationJpaRepository jpaRepository, JPAQueryFactory jpaQueryFactory) {
        return new NotificationRepositoryAdapter(jpaRepository, jpaQueryFactory);
    }

    @Bean
        @ConditionalOnMissingBean(OutboxRepository.class)
        public OutboxRepository outboxRepository(OutboxJpaRepository jpaRepository, JPAQueryFactory jpaQueryFactory) {
            return new OutboxRepositoryAdapter(jpaRepository, jpaQueryFactory);
    }
}