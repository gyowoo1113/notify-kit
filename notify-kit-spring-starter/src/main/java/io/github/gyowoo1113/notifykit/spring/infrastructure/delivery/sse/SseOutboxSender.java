package io.github.gyowoo1113.notifykit.spring.infrastructure.delivery.sse;

import io.github.gyowoo1113.notifykit.core.domain.event.SseEvent;
import io.github.gyowoo1113.notifykit.core.domain.outbox.OutboxMessage;
import io.github.gyowoo1113.notifykit.core.service.port.OutboxSender;
import io.github.gyowoo1113.notifykit.core.service.port.RecentEventStore;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SseOutboxSender implements OutboxSender {

    private final SseEmitterRegistry registry;
    private final RecentEventStore recentEventStore;

    @Override
    public void send(OutboxMessage message) {
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

        recentEventStore.append(receiverId,event);
        registry.send(receiverId, eventId,"notification", payloadJson);
    }
}
