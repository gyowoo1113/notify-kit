package io.github.gyowoo1113.notifykit.core.service.port.noop;

import io.github.gyowoo1113.notifykit.core.domain.outbox.OutboxMessage;
import io.github.gyowoo1113.notifykit.core.service.port.OutboxSender;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public class NoopOutboxSender implements OutboxSender {
    private final AtomicBoolean warned = new AtomicBoolean(false);

    @Override
    public CompletableFuture<Void> send(OutboxMessage message) {

        if (warned.compareAndSet(false, true)) {
            log.warn("NotifyKit is running with NoopOutboxSender. Messages will not be delivered.");
        }

        return CompletableFuture.completedFuture(null);
    }
}
