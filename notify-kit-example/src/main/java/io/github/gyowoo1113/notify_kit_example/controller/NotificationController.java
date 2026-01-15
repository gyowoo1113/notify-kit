package io.github.gyowoo1113.notify_kit_example.controller;

import io.github.gyowoo1113.notifykit.core.domain.Notification;
import io.github.gyowoo1113.notifykit.core.domain.support.NotificationCreate;
import io.github.gyowoo1113.notifykit.core.domain.support.NotificationStatus;
import io.github.gyowoo1113.notifykit.core.domain.support.NotificationUpdate;
import io.github.gyowoo1113.notifykit.core.support.PageResult;
import io.github.gyowoo1113.notifykit.spring.application.NotificationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notification")
public class NotificationController {
    private final NotificationFacade facade;

    @PostMapping
    public ResponseEntity<Notification> create(@RequestBody NotificationCreate notificationCreate) {
        Notification saved = facade.create(notificationCreate);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Notification> get(@PathVariable Long id) {
        Notification notification = facade.getById(id);
        return ResponseEntity.ok(notification);
    }

    @GetMapping("/list")
    public PageResult<Notification> list(
            @RequestParam Long receiverId,
            @RequestParam(required = false) NotificationStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        return facade.list(receiverId, status, page, size);
    }


    @PatchMapping("/{id}")
    public ResponseEntity<Notification> update(@PathVariable Long id, @RequestBody NotificationUpdate notificationUpdate) {
        Notification notification = facade.update(id, notificationUpdate);
        return ResponseEntity.ok(notification);
    }

    @PatchMapping("/{id}/read")
    public ResponseEntity<Notification> markAsRead(@PathVariable Long id) {
        Notification notification = facade.markAsRead(id);
        return ResponseEntity.ok(notification);
    }

    @PatchMapping("/{id}/unread")
    public ResponseEntity<Notification> markAsUnread(@PathVariable Long id) {
        Notification notification = facade.markAsUnread(id);
        return ResponseEntity.ok(notification);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        facade.delete(id);
        return ResponseEntity.noContent().build();
    }
}
