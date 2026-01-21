package io.github.gyowoo1113.notifykit.core.domain.notification;

import java.time.Instant;

public record NotificationUpdate(
        String title,
        String content,
        String linkUrl,
        NotificationStatus notificationStatus,
        Instant updatedAt,
        Instant readAt
) {
}
