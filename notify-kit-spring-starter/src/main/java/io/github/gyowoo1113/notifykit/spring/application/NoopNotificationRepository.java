package io.github.gyowoo1113.notifykit.spring.application;

import io.github.gyowoo1113.notifykit.core.domain.notification.Notification;
import io.github.gyowoo1113.notifykit.core.domain.notification.NotificationStatus;
import io.github.gyowoo1113.notifykit.core.service.port.NotificationRepository;
import io.github.gyowoo1113.notifykit.core.support.CursorPage;
import io.github.gyowoo1113.notifykit.core.support.PageResult;

import java.util.List;
import java.util.Optional;

public class NoopNotificationRepository implements NotificationRepository {

    @Override
    public Notification save(Notification notification) {
        return notification;
    }

    @Override
    public Optional<Notification> getById(long id) {
        return Optional.empty();
    }

    @Override
    public PageResult<Notification> list(long receiverId, NotificationStatus status, int page, int size) {
        return new PageResult<>(
                List.of(),   // content
                page,        // page
                size,        // size
                0,           // totalElements
                0,           // totalPages
                false        // hasNext
        );
    }

    @Override
    public CursorPage<Notification> listCursor(long receiverId, NotificationStatus status, Long cursor, int size) {
        return new CursorPage<>(List.of(), null, false);
    }

    @Override
    public long countUnread(long receiverId) {
        return 0;
    }
}
