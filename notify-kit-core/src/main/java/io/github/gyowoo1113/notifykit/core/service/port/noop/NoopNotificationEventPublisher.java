package io.github.gyowoo1113.notifykit.core.service.port.noop;

import io.github.gyowoo1113.notifykit.core.domain.notification.Notification;
import io.github.gyowoo1113.notifykit.core.service.port.NotificationEventPublisher;

public class NoopNotificationEventPublisher implements NotificationEventPublisher {
    @Override
    public void publishCreated(Notification notification) {

    }
}
