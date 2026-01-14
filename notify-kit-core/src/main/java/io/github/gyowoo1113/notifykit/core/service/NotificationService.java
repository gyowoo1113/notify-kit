package io.github.gyowoo1113.notifykit.core.service;

import io.github.gyowoo1113.notifykit.core.domain.Notification;
import io.github.gyowoo1113.notifykit.core.domain.support.NotificationCreate;
import io.github.gyowoo1113.notifykit.core.domain.support.NotificationUpdate;
import io.github.gyowoo1113.notifykit.core.service.port.NotificationRepository;
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
        // TODO : add ResourceNotFoundException
        return repository.getById(id).orElseThrow();
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
}
