package io.github.gyowoo1113.notifykit.spring.infrastructure.delivery.event;

import io.github.gyowoo1113.notifykit.core.domain.Notification;

public class NoopNotificationEventPublisher implements NotificationEventPublisher {
    @Override
    public void publishCreated(Notification notification) {

    }
}
