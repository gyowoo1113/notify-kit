package io.github.gyowoo1113.notifykit.core.service.port.noop;

import io.github.gyowoo1113.notifykit.core.domain.notification.Notification;
import io.github.gyowoo1113.notifykit.core.domain.outbox.OutboxMessage;
import io.github.gyowoo1113.notifykit.core.service.port.OutboxSender;

public class NoopOutboxSender implements OutboxSender {
    @Override
    public void send(OutboxMessage message) {

    }
}
