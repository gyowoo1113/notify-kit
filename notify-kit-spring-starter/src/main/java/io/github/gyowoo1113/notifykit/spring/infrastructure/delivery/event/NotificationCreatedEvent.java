package io.github.gyowoo1113.notifykit.spring.infrastructure.delivery.event;

import io.github.gyowoo1113.notifykit.core.domain.Notification;

public record NotificationCreatedEvent(Notification notification) {
}
