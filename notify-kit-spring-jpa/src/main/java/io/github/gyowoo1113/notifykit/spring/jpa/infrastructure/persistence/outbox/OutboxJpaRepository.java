package io.github.gyowoo1113.notifykit.spring.jpa.infrastructure.persistence.outbox;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OutboxJpaRepository extends JpaRepository<OutboxMessageEntity,Long> {
}
