package io.github.gyowoo1113.notifykit.spring.jpa.infrastructure.persistence.outbox;

import io.github.gyowoo1113.notifykit.core.domain.outbox.AggregateType;
import io.github.gyowoo1113.notifykit.core.domain.outbox.OutboxEventType;
import io.github.gyowoo1113.notifykit.core.domain.outbox.OutboxStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name="outbox_messages")
public class OutboxMessageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="aggregateType")
    private AggregateType aggregateType;

    @Column(name="aggregateId")
    private Long aggregateId;

    @Column(name="outboxEventType")
    private OutboxEventType outboxEventType;

    @Column(name="receiverId")
    private Long receiverId;

    @Column(name="payloadJson")
    private String payloadJson;

    @Column(name="schemaVersion")
    private int schemaVersion;    // payload version number

    @Column(name="outboxStatus")
    private OutboxStatus outboxStatus;

    @Column(name="retryCount")
    private int retryCount;

    @Column(name="nextRetryAt")
    private Instant nextRetryAt;

    @Column(name="createdAt")
    private Instant createdAt;

    @Column(name="processedAt")
    private Instant processedAt;
}
