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
import io.github.gyowoo1113.notifykit.core.service.port.OutboxSender;
import io.github.gyowoo1113.notifykit.spring.api.response.NotificationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@RequiredArgsConstructor
public class NotificationCreatedEventListener {
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void trySend(NotificationCreatedEvent event) {

    }
}
