package io.github.gyowoo1113.notify_kit_example.controller;

import io.github.gyowoo1113.notifykit.core.domain.Notification;
import io.github.gyowoo1113.notifykit.core.domain.support.NotificationCreate;
import io.github.gyowoo1113.notifykit.core.domain.support.NotificationUpdate;
import io.github.gyowoo1113.notifykit.core.support.PageResult;
import io.github.gyowoo1113.notifykit.spring.api.response.NotificationResponse;
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
    public ResponseEntity<NotificationResponse> create(@RequestBody NotificationCreate notificationCreate) {
        Notification saved = facade.create(notificationCreate);
        return ResponseEntity.ok(NotificationResponse.from(saved));
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificationResponse> get(@PathVariable Long id) {
        Notification notification = facade.getById(id);
        return ResponseEntity.ok(NotificationResponse.from(notification));
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
    public ResponseEntity<NotificationResponse> update(@PathVariable Long id, @RequestBody NotificationUpdate notificationUpdate) {
        Notification notification = facade.update(id, notificationUpdate);
        return ResponseEntity.ok(NotificationResponse.from(notification));
    }

    @PatchMapping("/{id}/read")
    public ResponseEntity<NotificationResponse> markAsRead(@PathVariable Long id) {
        Notification notification = facade.markAsRead(id);
        return ResponseEntity.ok(NotificationResponse.from(notification));
    }

    @PatchMapping("/{id}/unread")
    public ResponseEntity<NotificationResponse> markAsUnread(@PathVariable Long id) {
        Notification notification = facade.markAsUnread(id);
        return ResponseEntity.ok(NotificationResponse.from(notification));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        facade.delete(id);
        return ResponseEntity.noContent().build();
    }
}
