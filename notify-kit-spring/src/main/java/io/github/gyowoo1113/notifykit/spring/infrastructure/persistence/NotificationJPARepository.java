package io.github.gyowoo1113.notifykit.spring.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NotificationJPARepository extends JpaRepository<NotificationEntity,Long> {
    // Optional<NotificationEntity>
}
