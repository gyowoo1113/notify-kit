package io.github.gyowoo1113.notifykit.spring.infrastructure.delivery.channel.slack;

import io.github.gyowoo1113.notifykit.core.domain.outbox.OutboxMessage;
import io.github.gyowoo1113.notifykit.spring.infrastructure.delivery.channel.slack.port.SlackTargetResolver;

public class DefaultSlackTargetResolver implements SlackTargetResolver {
    @Override
    public String resolve(OutboxMessage message) {
        return "#general";
    }
}
