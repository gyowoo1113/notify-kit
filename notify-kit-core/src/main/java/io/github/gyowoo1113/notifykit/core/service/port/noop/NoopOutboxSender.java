package io.github.gyowoo1113.notifykit.core.service.port.noop;

import io.github.gyowoo1113.notifykit.core.domain.notification.Notification;
import io.github.gyowoo1113.notifykit.core.domain.outbox.OutboxMessage;
import io.github.gyowoo1113.notifykit.core.service.port.OutboxSender;

import java.util.concurrent.CompletableFuture;

public class NoopOutboxSender implements OutboxSender {
    @Override
    public CompletableFuture<Void> send(OutboxMessage message) {
        return CompletableFuture.completedFuture(null);
    }
}
