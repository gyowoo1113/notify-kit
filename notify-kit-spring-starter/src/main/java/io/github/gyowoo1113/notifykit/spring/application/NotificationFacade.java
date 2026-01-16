package io.github.gyowoo1113.notifykit.spring.application;

import io.github.gyowoo1113.notifykit.core.domain.Notification;
import io.github.gyowoo1113.notifykit.core.domain.support.NotificationCreate;
import io.github.gyowoo1113.notifykit.core.domain.support.NotificationStatus;
import io.github.gyowoo1113.notifykit.core.domain.support.NotificationUpdate;
import io.github.gyowoo1113.notifykit.core.service.NotificationService;
import io.github.gyowoo1113.notifykit.core.support.CursorPage;
import io.github.gyowoo1113.notifykit.core.support.PageResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NotificationFacade {
    private final NotificationService coreService;

    @Transactional
    public Notification create(NotificationCreate create) {
        return coreService.create(create);
    }

    @Transactional(readOnly = true)
    public Notification getById(Long id) {
        return coreService.getById(id);
    }

    @Transactional(readOnly = true)
    public PageResult<Notification> list(Long receiverId, NotificationStatus status, int page, int size) {
        return coreService.list(receiverId, status, page, size);
    }

    @Transactional(readOnly = true)
    public CursorPage<Notification> listCursor(Long receiverId, NotificationStatus status, Long cursor, int size) {
        return coreService.listCursor(receiverId, status, cursor, size);
    }

    @Transactional
    public Notification update(Long id, NotificationUpdate update) {
        return coreService.update(id, update);
    }

    @Transactional
    public Notification markAsRead(Long id) {
        return coreService.markAsRead(id);
    }

    @Transactional
    public Notification markAsUnread(Long id) {
        return coreService.markAsUnread(id);
    }

    @Transactional
    public void delete(Long id) {
        coreService.delete(id);
    }
}
