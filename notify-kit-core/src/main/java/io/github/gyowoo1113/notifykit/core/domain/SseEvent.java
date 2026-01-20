package io.github.gyowoo1113.notifykit.core.domain;

public record SseEvent(
        String eventId,
        long receiverId,
        long notificationId,
        Object payload,
        long createdAtEpochMs
) {
}
