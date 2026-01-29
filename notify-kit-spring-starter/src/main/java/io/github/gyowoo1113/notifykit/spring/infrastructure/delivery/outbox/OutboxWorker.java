package io.github.gyowoo1113.notifykit.spring.infrastructure.delivery.outbox;

import io.github.gyowoo1113.notifykit.core.domain.outbox.OutboxMessage;
import io.github.gyowoo1113.notifykit.core.exception.OutboxException;
import io.github.gyowoo1113.notifykit.core.service.OutboxProcessorService;
import io.github.gyowoo1113.notifykit.core.service.port.OutboxRepository;
import io.github.gyowoo1113.notifykit.core.service.port.OutboxSender;
import io.github.gyowoo1113.notifykit.spring.config.properties.OutboxProperties;
import lombok.RequiredArgsConstructor;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@RequiredArgsConstructor
public class OutboxWorker {
    private final OutboxProcessorService processorService;
    private final OutboxProperties properties;

    public void process() {
        processorService.processBatch(Instant.now(),
                properties.getBatchSize(),
                properties.getRetryLimit(),
                properties.getRetryDelay());
    }
}
