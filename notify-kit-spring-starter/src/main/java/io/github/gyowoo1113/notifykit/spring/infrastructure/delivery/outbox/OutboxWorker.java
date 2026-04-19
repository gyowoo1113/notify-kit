package io.github.gyowoo1113.notifykit.spring.infrastructure.delivery.outbox;


import io.github.gyowoo1113.notifykit.spring.application.OutboxProcessorFacade;
import io.github.gyowoo1113.notifykit.spring.config.properties.OutboxProperties;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

@RequiredArgsConstructor
public class OutboxWorker {
    private final OutboxProcessorFacade processorFacade;
    private final OutboxProperties properties;

    public void process() {
        processorFacade.processBatch(Instant.now(),
                properties.getBatchSize(),
                properties.getRetryLimit(),
                properties.getRetryDelay());
    }
}
