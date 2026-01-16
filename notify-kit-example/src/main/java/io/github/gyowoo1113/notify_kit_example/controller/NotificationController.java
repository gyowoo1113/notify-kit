package io.github.gyowoo1113.notify_kit_example.controller;

import io.github.gyowoo1113.notifykit.core.domain.Notification;
import io.github.gyowoo1113.notifykit.core.domain.support.NotificationCreate;
import io.github.gyowoo1113.notifykit.core.domain.support.NotificationStatus;
import io.github.gyowoo1113.notifykit.core.domain.support.NotificationUpdate;
import io.github.gyowoo1113.notifykit.core.support.CursorPage;
import io.github.gyowoo1113.notifykit.core.support.PageRequestSpec;
import io.github.gyowoo1113.notifykit.core.support.PageResult;
import io.github.gyowoo1113.notifykit.spring.api.request.NotificationListRequest;
import io.github.gyowoo1113.notifykit.spring.api.response.NotificationResponse;
import io.github.gyowoo1113.notifykit.spring.api.response.UnreadCountResponse;
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

    @GetMapping
    public PageResult<NotificationResponse> list(@ModelAttribute NotificationListRequest request) {
        PageRequestSpec spec = request.toPageSpec(5, 50);
        PageResult<Notification> result = facade.list(request.receiverIdRequired(), request.status(), spec.page(), spec.size());
        return result.map(NotificationResponse::from);
    }

    @GetMapping("/cursor")
    public CursorPage<NotificationResponse> listCursor(
            @RequestParam long receiverId,
            @RequestParam(required = false) NotificationStatus status,
            @RequestParam(required = false) Long cursor,
            @RequestParam(defaultValue = "5") int size
    ) {
        CursorPage<Notification> result = facade.listCursor(receiverId, status, cursor, size);
        return result.map(NotificationResponse::from);
    }

    @GetMapping("/unread-count/{receiverId}")
    public ResponseEntity<UnreadCountResponse> countUnread(@PathVariable Long receiverId) {
        return ResponseEntity.ok(
                new UnreadCountResponse(facade.countUnread(receiverId))
        );
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
