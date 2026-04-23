package io.github.gyowoo1113.notifykit.spring.infrastructure.delivery.channel.slack.port;

import io.github.gyowoo1113.notifykit.core.domain.outbox.OutboxMessage;

public interface SlackTargetResolver {
    String resolve(OutboxMessage message);
}
