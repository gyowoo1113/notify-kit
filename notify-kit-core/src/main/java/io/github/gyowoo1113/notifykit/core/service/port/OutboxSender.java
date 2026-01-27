package io.github.gyowoo1113.notifykit.core.service.port;

import io.github.gyowoo1113.notifykit.core.domain.outbox.OutboxMessage;

public interface OutboxSender {
    void send(OutboxMessage message);
}
