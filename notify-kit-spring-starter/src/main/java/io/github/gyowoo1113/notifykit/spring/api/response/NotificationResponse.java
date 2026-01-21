package io.github.gyowoo1113.notifykit.spring.api.response;

import io.github.gyowoo1113.notifykit.core.domain.notification.Notification;
import io.github.gyowoo1113.notifykit.core.domain.notification.NotificationStatus;
import io.github.gyowoo1113.notifykit.core.domain.notification.NotificationType;

import java.time.Instant;

public record NotificationResponse(
        Long id,
        Long receiverId,
        String title,
        String content,
        NotificationType notificationType,
        NotificationStatus notificationStatus,
        String linkUrl,
        Instant createdAt,
        Instant readAt,
        Instant updatedAt,
        Instant deletedAt
) {
    public static NotificationResponse from(Notification notification) {
        return new NotificationResponse(
                notification.getId(),
                notification.getReceiverId(),
                notification.getTitle(),
                notification.getContent(),
                notification.getNotificationType(),
                notification.getNotificationStatus(),
                notification.getLinkUrl(),
                notification.getCreatedAt(),
                notification.getReadAt(),
                notification.getUpdatedAt(),
                notification.getDeletedAt()
        );
    }
}
