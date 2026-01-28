package io.github.gyowoo1113.notifykit.spring.infrastructure.delivery.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.gyowoo1113.notifykit.core.domain.notification.Notification;
import io.github.gyowoo1113.notifykit.core.domain.outbox.AggregateType;
import io.github.gyowoo1113.notifykit.core.domain.outbox.OutboxEventType;
import io.github.gyowoo1113.notifykit.core.domain.outbox.OutboxMessage;
import io.github.gyowoo1113.notifykit.core.domain.outbox.OutboxStatus;
import io.github.gyowoo1113.notifykit.core.exception.OutboxException;
import io.github.gyowoo1113.notifykit.core.service.port.EventIdGenerator;
import io.github.gyowoo1113.notifykit.core.service.port.OutboxRepository;
import io.github.gyowoo1113.notifykit.spring.api.response.NotificationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@RequiredArgsConstructor
public class NotificationCreatedEventListener {
    private final OutboxRepository outboxRepository;
    private final ObjectMapper objectMapper;
    private final EventIdGenerator eventIdGenerator;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onCreated(NotificationCreatedEvent event) {
        Notification notification = event.notification();
        OutboxEventType eventType = OutboxEventType.NOTIFICATION_CREATED;

        try {
            String payloadJson = objectMapper.writeValueAsString(
                    NotificationResponse.from(notification)
            );

            OutboxMessage outbox = OutboxMessage.builder()
                    .aggregateType(AggregateType.NOTIFICATION)
                    .aggregateId(notification.getId())
                    .outboxEventType(OutboxEventType.NOTIFICATION_CREATED)
                    .receiverId(notification.getReceiverId())
                    .payloadJson(payloadJson)
                    .schemaVersion(1)
                    .outboxStatus(OutboxStatus.PENDING)
                    .retryCount(0)
                    .createdAt(notification.getCreatedAt())
                    .eventId(eventIdGenerator.nextId())
                    .build();

            outboxRepository.save(outbox);
        } catch (JsonProcessingException e) {
            log.error("Outbox serialize failed. notificationId={}, receiverId={}, eventType={}",
                    notification.getId(), notification.getReceiverId(), eventType, e);

            throw OutboxException.serializeFailed(eventType.name());
        } catch (Exception e){
            log.error("Outbox save failed. notificationId={}, receiverId={}, eventType={}",
                    notification.getId(), notification.getReceiverId(), eventType, e);
            throw OutboxException.saveFailed(eventType.name());
        }
    }
}
