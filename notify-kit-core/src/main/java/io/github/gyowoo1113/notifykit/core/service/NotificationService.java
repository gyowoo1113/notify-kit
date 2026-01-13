package io.github.gyowoo1113.notifykit.core.service;

import io.github.gyowoo1113.notifykit.core.domain.Notification;
import io.github.gyowoo1113.notifykit.core.domain.support.NotificationCreate;
import io.github.gyowoo1113.notifykit.core.service.port.NotificationRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository repository;

    public Notification create(NotificationCreate create){
        Notification notification = Notification.from(create);
        notification = repository.save(notification);
        return notification;
    }

    public Notification getById(long id){
        // TODO : add ResourceNotFoundException
        return repository.getById(id).orElseThrow();
    }
}
