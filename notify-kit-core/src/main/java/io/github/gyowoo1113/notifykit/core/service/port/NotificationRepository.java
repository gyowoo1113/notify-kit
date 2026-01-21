package io.github.gyowoo1113.notifykit.core.service.port;

import io.github.gyowoo1113.notifykit.core.domain.notification.Notification;
import io.github.gyowoo1113.notifykit.core.domain.notification.NotificationStatus;
import io.github.gyowoo1113.notifykit.core.support.CursorPage;
import io.github.gyowoo1113.notifykit.core.support.PageResult;

import java.util.Optional;

public interface NotificationRepository {
    Notification save(Notification notification);
    Optional<Notification> getById(long id);
    PageResult<Notification> list(long receiverId, NotificationStatus status, int page, int size);
    CursorPage<Notification> listCursor(long receiverId, NotificationStatus status, Long cursor, int size);
    long countUnread(long receiverId);
}
