package io.github.gyowoo1113.notifykit.core.domain;

public record SseEvent(
        long eventId,
        long receiverId,
        long notificationId,
        Object payload,
        long createdAtEpochMs
) {
}
