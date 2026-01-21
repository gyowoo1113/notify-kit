package io.github.gyowoo1113.notifykit.core.domain.notification;

import io.github.gyowoo1113.notifykit.core.exception.ValidationException;

public record NotificationCreate(
        Long receiverId,
        String title,
        String content,
        NotificationType notificationType,
        String linkUrl
) {
    public NotificationCreate{
        if (title == null) {
            throw new ValidationException("notification", "title", "required");
        }
    }
}
