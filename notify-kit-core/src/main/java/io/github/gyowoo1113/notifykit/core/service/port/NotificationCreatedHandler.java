package io.github.gyowoo1113.notifykit.core.service.port;

import io.github.gyowoo1113.notifykit.core.domain.notification.Notification;

public interface NotificationCreatedHandler {
    void handle(Notification notification);
}
