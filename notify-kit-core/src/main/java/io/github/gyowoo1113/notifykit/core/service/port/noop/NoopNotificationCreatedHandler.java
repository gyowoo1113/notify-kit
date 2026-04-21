package io.github.gyowoo1113.notifykit.core.service.port.noop;


import io.github.gyowoo1113.notifykit.core.domain.notification.Notification;
import io.github.gyowoo1113.notifykit.core.service.port.NotificationCreatedHandler;

public class NoopNotificationCreatedHandler implements NotificationCreatedHandler {

    @Override
    public void handle(Notification notification) {

    }
}
