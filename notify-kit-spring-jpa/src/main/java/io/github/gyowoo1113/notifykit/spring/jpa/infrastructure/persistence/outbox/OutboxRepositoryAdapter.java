package io.github.gyowoo1113.notifykit.spring.jpa.infrastructure.persistence.outbox;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.github.gyowoo1113.notifykit.core.domain.outbox.OutboxEventType;
import io.github.gyowoo1113.notifykit.core.domain.outbox.OutboxMessage;
import io.github.gyowoo1113.notifykit.core.domain.outbox.OutboxStatus;
import io.github.gyowoo1113.notifykit.core.service.port.OutboxRepository;
import lombok.RequiredArgsConstructor;
import static io.github.gyowoo1113.notifykit.spring.jpa.infrastructure.persistence.outbox.QOutboxMessageEntity.outboxMessageEntity;

import java.time.Instant;
import java.util.List;

@RequiredArgsConstructor
public class OutboxRepositoryAdapter implements OutboxRepository {

    private final OutboxJpaRepository repository;
    private final JPAQueryFactory jpaQueryFactory;

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
    public boolean markProcessing(Long id)
    {
        Instant now = Instant.now();
        long updated = jpaQueryFactory
                .update(outboxMessageEntity)
                .set(outboxMessageEntity.outboxStatus, OutboxStatus.PROCESSING)
                .set(outboxMessageEntity.processedAt, now)
                .where(
                        outboxMessageEntity.id.eq(id),
                        outboxMessageEntity.outboxStatus.eq(OutboxStatus.PENDING)
                )
                .execute();
        return updated == 1;
    }

    @Override
    public boolean markSent(Long id) {
        Instant now = Instant.now();
        long updated = jpaQueryFactory
                .update(outboxMessageEntity)
                .set(outboxMessageEntity.outboxStatus,OutboxStatus.SENT)
                .set(outboxMessageEntity.processedAt,now)
                .where(
                        outboxMessageEntity.id.eq(id),
                        outboxMessageEntity.outboxStatus.eq(OutboxStatus.PROCESSING)
                )
                .execute();
        return updated == 1;
    }

    @Override
    public boolean markFailed(Long id, Instant nextRetryAt, String errorMessage) {
        Instant now = Instant.now();
        long updated = jpaQueryFactory
                .update(outboxMessageEntity)
                .set(outboxMessageEntity.outboxStatus,OutboxStatus.FAILED)
                .set(outboxMessageEntity.processedAt,now)
                .set(outboxMessageEntity.nextRetryAt,nextRetryAt)
                .set(outboxMessageEntity.retryCount,outboxMessageEntity.retryCount.add(1))
                .where(
                        outboxMessageEntity.id.eq(id),
                        outboxMessageEntity.outboxStatus.eq(OutboxStatus.PROCESSING)
                )
                .execute();
        return updated == 1;
    }
}
