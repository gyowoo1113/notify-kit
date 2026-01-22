package io.github.gyowoo1113.notifykit.spring.jpa.infrastructure.persistence.outbox;

import io.github.gyowoo1113.notifykit.core.domain.outbox.OutboxMessage;
import io.github.gyowoo1113.notifykit.core.service.port.OutboxRepository;

import java.time.Instant;
import java.util.List;

public class OutboxRepositoryAdapter implements OutboxRepository {
    @Override
    public void save(OutboxMessage msg) {

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
