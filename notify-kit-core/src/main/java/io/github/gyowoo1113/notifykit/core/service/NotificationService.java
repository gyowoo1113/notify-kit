package io.github.gyowoo1113.notifykit.core.service;

import io.github.gyowoo1113.notifykit.core.domain.notification.Notification;
import io.github.gyowoo1113.notifykit.core.domain.notification.NotificationCreate;
import io.github.gyowoo1113.notifykit.core.domain.notification.NotificationStatus;
import io.github.gyowoo1113.notifykit.core.domain.notification.NotificationUpdate;
import io.github.gyowoo1113.notifykit.core.exception.ResourceNotFoundException;
import io.github.gyowoo1113.notifykit.core.service.port.NotificationRepository;
import io.github.gyowoo1113.notifykit.core.support.CursorPage;
import io.github.gyowoo1113.notifykit.core.support.PageResult;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository repository;

    public Notification create(NotificationCreate create){
        Notification notification = Notification.create(create, Instant.now());
        notification = repository.save(notification);
        return notification;
    }

    public Notification getById(long id){
        Notification notification = repository.getById(id)
                .orElseThrow(() -> notFound(id));

        assertVisible(notification,id);

        return notification;
    }

    public PageResult<Notification> list(long receiverId, NotificationStatus status, int page, int size){
        return repository.list(receiverId, status, page, size);
    }

    public CursorPage<Notification> listCursor(long receiverId, NotificationStatus status, Long cursor, int size){
        return repository.listCursor(receiverId, status, cursor, size);
    }

    public long countUnread(long receiverId){
        return repository.countUnread(receiverId);
    }

    public Notification update(long id, NotificationUpdate notificationUpdate){
        Notification notification = getById(id);
        notification = notification.update(notificationUpdate, Instant.now());
        notification = repository.save(notification);
        return notification;
    }

    public Notification markAsRead(long id){
        Notification notification = getById(id);
        notification = notification.markAsRead(Instant.now());
        notification = repository.save(notification);
        return notification;
    }

    public Notification markAsUnread(long id){
        Notification notification = getById(id);
        notification = notification.markAsUnread(Instant.now());
        notification = repository.save(notification);
        return notification;
    }

    public void delete(long id){
        Notification notification = repository.getById(id)
                .orElseThrow(() -> notFound(id));
        if (notification.isDeleted()) return;

        notification = notification.markAsDeleted(Instant.now());
        repository.save(notification);
    }

    private ResourceNotFoundException notFound(long id){
        return new ResourceNotFoundException("notification", String.valueOf(id));
    }

    private void assertVisible(Notification notification, long id){
        if (notification.isDeleted()){
            throw notFound(id);
        }
    }
}
