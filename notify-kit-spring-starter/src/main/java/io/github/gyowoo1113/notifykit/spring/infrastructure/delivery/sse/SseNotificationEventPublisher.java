package io.github.gyowoo1113.notifykit.spring.infrastructure.delivery.sse;

import io.github.gyowoo1113.notifykit.core.domain.notification.Notification;
import io.github.gyowoo1113.notifykit.core.domain.event.SseEvent;
import io.github.gyowoo1113.notifykit.core.service.port.RecentEventStore;
import io.github.gyowoo1113.notifykit.spring.api.response.NotificationResponse;
import io.github.gyowoo1113.notifykit.core.service.port.NotificationEventPublisher;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SseNotificationEventPublisher implements NotificationEventPublisher {

    private final SseEmitterRegistry registry;
    private final RecentEventStore recentEventStore;
    private final AtomicEventIdGenerator eventIdGenerator;

    @Override
    public void publishCreated(Notification notification) {
        long receiverId = notification.getReceiverId();
        long eventId = eventIdGenerator.nextId();
        Object payload = NotificationResponse.from(notification);

        SseEvent event = new SseEvent(
                eventId,
                receiverId,
                notification.getId(),
                payload,
                System.currentTimeMillis()
        );

        recentEventStore.append(receiverId,event);
        registry.send(receiverId, eventId,"notification", payload);
    }
}
