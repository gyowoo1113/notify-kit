package io.github.gyowoo1113.notifykit.spring.infrastructure.delivery.channel.slack;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.gyowoo1113.notifykit.core.domain.notification.NotificationPayload;
import io.github.gyowoo1113.notifykit.core.domain.outbox.OutboxMessage;
import io.github.gyowoo1113.notifykit.core.exception.NotRetryableException;
import io.github.gyowoo1113.notifykit.core.service.port.OutboxSender;
import io.github.gyowoo1113.notifykit.spring.infrastructure.delivery.channel.slack.port.SlackTargetResolver;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class SlackOutboxSender implements OutboxSender {

    private final SlackClient slackClient;
    private final SlackTargetResolver targetResolver;
    private final ObjectMapper objectMapper;

    @Override
    public CompletableFuture<Void> send(OutboxMessage message) {
        String channel = targetResolver.resolve(message);
        String text = extractMessage(message);
        return slackClient.send(channel, text);
    }

    private String extractMessage(OutboxMessage message){
        try {
            NotificationPayload payload = objectMapper.readValue(message.getPayloadJson(), NotificationPayload.class);

            return Stream.of(payload.title(), payload.content(), payload.linkUrl())
                    .filter(s -> s != null && !s.isBlank())
                    .collect(Collectors.joining("\n"));
        } catch (Exception e){
            throw new NotRetryableException("slack", e);
        }
    }
}
