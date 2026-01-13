package io.github.gyowoo1113.notifykit.core.domain;

import io.github.gyowoo1113.notifykit.core.domain.support.NotificationCreate;
import io.github.gyowoo1113.notifykit.core.domain.support.NotificationStatus;
import io.github.gyowoo1113.notifykit.core.domain.support.NotificationType;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
public class Notification {
    private final Long id;
    private final Long receiverId;
    private final String title;
    private final String content;
    private final NotificationType notificationType;
    private final NotificationStatus notificationStatus;
    private final String linkUrl;
    private final Instant createdAt;
    private final Instant readAt;
    private final Instant updatedAt;
    private final Instant deletedAt;

    @Builder
    public Notification(Long id, Long receiverId, String title, String content, NotificationType notificationType, NotificationStatus notificationStatus, String linkUrl, Instant createdAt, Instant readAt, Instant updatedAt, Instant deletedAt) {
        this.id = id;
        this.receiverId = receiverId;
        this.title = title;
        this.content = content;
        this.notificationType = notificationType;
        this.notificationStatus = notificationStatus;
        this.linkUrl = linkUrl;
        this.createdAt = createdAt;
        this.readAt = readAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public static Notification from(NotificationCreate notificationCreate){
        return Notification.builder()
                .receiverId(notificationCreate.receiverId())
                .title(notificationCreate.title())
                .content(notificationCreate.content())
                .notificationType(notificationCreate.notificationType())
                .notificationStatus(notificationCreate.notificationStatus())
                .linkUrl(notificationCreate.linkUrl())
                .createdAt(notificationCreate.createdAt())
                .readAt(notificationCreate.readAt())
                .updatedAt(notificationCreate.updatedAt())
                .deletedAt(notificationCreate.deletedAt())
                .build();
    }

    public Notification update(NotificationCreate notificationCreate){
        return Notification.builder()
                .id(id)
                .title(title)
                .content(content)
                .notificationType(notificationType)
                .notificationStatus(notificationCreate.notificationStatus())
                .linkUrl(linkUrl)
                .createdAt(createdAt)
                .readAt(notificationCreate.readAt())
                .deletedAt(deletedAt)
                .build();
    }
}
