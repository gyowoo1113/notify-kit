package io.github.gyowoo1113.notifykit.core.service.port;

import io.github.gyowoo1113.notifykit.core.domain.outbox.OutboxMessage;

import java.time.Instant;
import java.util.List;

public interface OutboxRepository {
    public void save(OutboxMessage msg);
    public List<OutboxMessage> findBatchForProcessing(Instant now, int limit);
    public boolean markProcessing(Long id);
    public void markSent(Long id);
    public void markFailed(Long id, Instant nextRetryAt, String errorMessgae);
}
