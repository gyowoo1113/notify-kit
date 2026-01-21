package io.github.gyowoo1113.notifykit.core.domain.event;

public record SseEvent(
        long eventId,
        long receiverId,
        long notificationId,
        Object payload,
        long createdAtEpochMs
) {
}
