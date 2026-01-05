package io.github.gyowoo1113.notifykit.core.domain;

import io.github.gyowoo1113.notifykit.core.domain.support.NotificationStatus;
import io.github.gyowoo1113.notifykit.core.domain.support.NotificationType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Notification {
    private final Long id;
    private final Long receiverId;
    private final String title;
    private final String content;
    private final NotificationType notificationType;
    private final NotificationStatus notificationStatus;
    private final String linkUrl;
    private final LocalDateTime createdAt;
    private final LocalDateTime readAt;

    @Builder
    public Notification(Long id, Long receiverId, String title, String content, NotificationType notificationType, NotificationStatus notificationStatus, String linkUrl, LocalDateTime createdAt, LocalDateTime readAt) {
        this.id = id;
        this.receiverId = receiverId;
        this.title = title;
        this.content = content;
        this.notificationType = notificationType;
        this.notificationStatus = notificationStatus;
        this.linkUrl = linkUrl;
        this.createdAt = createdAt;
        this.readAt = readAt;
    }
}
