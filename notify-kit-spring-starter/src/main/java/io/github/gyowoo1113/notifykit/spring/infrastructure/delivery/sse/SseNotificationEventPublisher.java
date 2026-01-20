package io.github.gyowoo1113.notifykit.spring.infrastructure.delivery.sse;

import io.github.gyowoo1113.notifykit.core.domain.Notification;
import io.github.gyowoo1113.notifykit.spring.api.response.NotificationResponse;
import io.github.gyowoo1113.notifykit.core.service.port.NotificationEventPublisher;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SseNotificationEventPublisher implements NotificationEventPublisher {

    private final SseEmitterRegistry registry;

    @Override
    public void publishCreated(Notification notification) {
        registry.send(notification.getReceiverId(), "notification", NotificationResponse.from(notification));
    }
}
