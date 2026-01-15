package io.github.gyowoo1113.notifykit.core.service.port;

import io.github.gyowoo1113.notifykit.core.domain.Notification;
import io.github.gyowoo1113.notifykit.core.domain.support.NotificationStatus;
import io.github.gyowoo1113.notifykit.core.support.PageResult;

import java.util.Optional;

public interface NotificationRepository {
    Notification save(Notification notification);
    Optional<Notification> getById(long id);
    PageResult<Notification> list(long receiverId, NotificationStatus status, int page, int size);
}
