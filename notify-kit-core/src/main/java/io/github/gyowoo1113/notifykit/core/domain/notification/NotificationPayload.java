package io.github.gyowoo1113.notifykit.core.domain.notification;

public record NotificationPayload(
        Long receiverId,
        String title,
        String content,
        String linkUrl
) {
}
