package io.github.gyowoo1113.notifykit.spring.jpa.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationJpaRepository extends JpaRepository<NotificationEntity,Long> {

}
