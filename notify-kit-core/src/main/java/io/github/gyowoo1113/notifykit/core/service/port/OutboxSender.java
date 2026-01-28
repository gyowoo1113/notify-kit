package io.github.gyowoo1113.notifykit.core.service.port;

import io.github.gyowoo1113.notifykit.core.domain.outbox.OutboxMessage;

import java.util.concurrent.CompletableFuture;

public interface OutboxSender {
    CompletableFuture<Void> send(OutboxMessage message);
}
