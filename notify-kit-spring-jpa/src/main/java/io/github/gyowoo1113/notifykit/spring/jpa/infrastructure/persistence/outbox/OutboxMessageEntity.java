package io.github.gyowoo1113.notifykit.spring.jpa.infrastructure.persistence.outbox;

import io.github.gyowoo1113.notifykit.core.domain.outbox.AggregateType;
import io.github.gyowoo1113.notifykit.core.domain.outbox.OutboxEventType;
import io.github.gyowoo1113.notifykit.core.domain.outbox.OutboxMessage;
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

    @Enumerated(EnumType.STRING)
    @Column(name="aggregateType")
    private AggregateType aggregateType;

    @Column(name="aggregateId")
    private Long aggregateId;

    @Enumerated(EnumType.STRING)
    @Column(name="outboxEventType")
    private OutboxEventType outboxEventType;

    @Column(name="receiverId")
    private Long receiverId;

    @Column(name="payloadJson")
    private String payloadJson;

    @Column(name="schemaVersion")
    private int schemaVersion;    // payload version number

    @Enumerated(EnumType.STRING)
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

    public static OutboxMessageEntity from(OutboxMessage outboxMessage){
        OutboxMessageEntity entity = new OutboxMessageEntity();
        entity.id = outboxMessage.getId();
        entity.aggregateType = outboxMessage.getAggregateType();
        entity.aggregateId = outboxMessage.getAggregateId();
        entity.outboxEventType = outboxMessage.getOutboxEventType();
        entity.receiverId = outboxMessage.getReceiverId();
        entity.payloadJson = outboxMessage.getPayloadJson();
        entity.schemaVersion = outboxMessage.getSchemaVersion();
        entity.outboxStatus = outboxMessage.getOutboxStatus();
        entity.retryCount = outboxMessage.getRetryCount();
        entity.nextRetryAt = outboxMessage.getNextRetryAt();
        entity.createdAt = outboxMessage.getCreatedAt();
        entity.processedAt = outboxMessage.getProcessedAt();
        return entity;
    }

    public OutboxMessage toModel(){
        return OutboxMessage.builder()
                .id(id)
                .aggregateType(aggregateType)
                .aggregateId(aggregateId)
                .outboxEventType(outboxEventType)
                .receiverId(receiverId)
                .payloadJson(payloadJson)
                .schemaVersion(schemaVersion)
                .outboxStatus(outboxStatus)
                .retryCount(retryCount)
                .nextRetryAt(nextRetryAt)
                .createdAt(createdAt)
                .processedAt(processedAt)
                .build();
    }
}
