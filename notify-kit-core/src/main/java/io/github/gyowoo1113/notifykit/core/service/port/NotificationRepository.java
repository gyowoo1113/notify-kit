package io.github.gyowoo1113.notifykit.core.service.port;

import io.github.gyowoo1113.notifykit.core.domain.Notification;

import java.util.Optional;

public interface NotificationRepository {
    Notification save(Notification notification);
    Optional<Notification> getById(long id);
}
