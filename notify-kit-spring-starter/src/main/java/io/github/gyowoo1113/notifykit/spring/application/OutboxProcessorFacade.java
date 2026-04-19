package io.github.gyowoo1113.notifykit.spring.application;

import io.github.gyowoo1113.notifykit.core.service.OutboxProcessorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class OutboxProcessorFacade {
    private final OutboxProcessorService outboxProcessorService;

    @Transactional
    public void processBatch(Instant now, int batchSize, int retryLimit, Duration retryDelay) {
        outboxProcessorService.processBatch(now, batchSize, retryLimit, retryDelay);
    }
}
