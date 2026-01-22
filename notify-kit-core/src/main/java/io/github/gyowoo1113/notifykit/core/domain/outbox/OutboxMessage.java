package io.github.gyowoo1113.notifykit.core.domain.outbox;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder
public class OutboxMessage {
    private final Long id;
    private final AggregateType aggregateType;
    private final Long aggregateId;
    private final OutboxEventType outboxEventType;
    private final Long receiverId;
    private final String payloadJson;
    private final int schemaVersion;    // payload version number
    private final OutboxStatus outboxStatus;
    private final int retryCount;
    private final Instant nextRetryAt;
    private final Instant createdAt;
    private final Instant processedAt;

    public OutboxMessage(Long id, AggregateType aggregateType, Long aggregateId, OutboxEventType outboxEventType, Long receiverId, String payloadJson, int schemaVersion, OutboxStatus outboxStatus, int retryCount, Instant nextRetryAt, Instant createdAt, Instant processedAt) {
        this.id = id;
        this.aggregateType = aggregateType;
        this.aggregateId = aggregateId;
        this.outboxEventType = outboxEventType;
        this.receiverId = receiverId;
        this.payloadJson = payloadJson;
        this.schemaVersion = schemaVersion;
        this.outboxStatus = outboxStatus;
        this.retryCount = retryCount;
        this.nextRetryAt = nextRetryAt;
        this.createdAt = createdAt;
        this.processedAt = processedAt;
    }
}
