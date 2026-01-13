package io.github.gyowoo1113.notifykit.core.domain.support;

import java.time.LocalDateTime;

public record NotificationCreate(
        Long receiverId,
        String title,
        String content,
        NotificationType notificationType,
        NotificationStatus notificationStatus,
        String linkUrl,
        LocalDateTime createdAt,
        LocalDateTime readAt,
        LocalDateTime deletedAt
) {
}
