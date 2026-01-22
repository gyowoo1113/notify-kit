package io.github.gyowoo1113.notifykit.spring.jpa.infrastructure.persistence.outbox;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.github.gyowoo1113.notifykit.core.domain.outbox.OutboxMessage;
import io.github.gyowoo1113.notifykit.core.service.port.OutboxRepository;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.List;

@RequiredArgsConstructor
public class OutboxRepositoryAdapter implements OutboxRepository {

    OutboxJpaRepository repository;
    JPAQueryFactory jpaQueryFactory;

    @Override
    public OutboxMessage save(OutboxMessage msg) {
        OutboxMessageEntity entity = OutboxMessageEntity.from(msg);
        entity = repository.save(entity);
        return entity.toModel();
    }

    @Override
    public List<OutboxMessage> findBatchForProcessing(Instant now, int limit) {
        return List.of();
    }

    @Override
    public boolean markProcessing(Long id) {
        return false;
    }

    @Override
    public void markSent(Long id) {

    }

    @Override
    public void markFailed(Long id, Instant nextRetryAt, String errorMessgae) {

    }
}
