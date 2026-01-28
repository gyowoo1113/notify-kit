package io.github.gyowoo1113.notifykit.spring.infrastructure.delivery.sse;

import io.github.gyowoo1113.notifykit.core.domain.event.SseEvent;
import io.github.gyowoo1113.notifykit.core.domain.outbox.OutboxMessage;
import io.github.gyowoo1113.notifykit.core.service.port.OutboxSender;
import io.github.gyowoo1113.notifykit.core.service.port.RecentEventStore;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
public class SseOutboxSender implements OutboxSender {

    private final SseEmitterRegistry registry;
    private final RecentEventStore recentEventStore;

    @Override
    public CompletableFuture<Void> send(OutboxMessage message) {
        try {
            long receiverId = message.getReceiverId();
            long eventId = message.getEventId();
            String payloadJson = message.getPayloadJson();

            SseEvent event = new SseEvent(
                    eventId,
                    receiverId,
                    message.getAggregateId(),
                    payloadJson,
                    System.currentTimeMillis()
            );

            registry.send(receiverId, eventId, "notification", payloadJson);
            recentEventStore.append(receiverId, event);
            return CompletableFuture.completedFuture(null);
        } catch (Exception e){
            // TODO : 부분 실패 시 처리 필요
            return CompletableFuture.failedFuture(e);
        }
    }
}
