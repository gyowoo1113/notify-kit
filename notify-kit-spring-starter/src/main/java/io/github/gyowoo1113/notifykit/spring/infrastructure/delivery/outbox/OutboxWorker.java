package io.github.gyowoo1113.notifykit.spring.infrastructure.delivery.outbox;

import io.github.gyowoo1113.notifykit.core.domain.outbox.OutboxMessage;
import io.github.gyowoo1113.notifykit.core.exception.OutboxException;
import io.github.gyowoo1113.notifykit.core.service.OutboxProcessorService;
import io.github.gyowoo1113.notifykit.core.service.port.OutboxRepository;
import io.github.gyowoo1113.notifykit.core.service.port.OutboxSender;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.List;

@RequiredArgsConstructor
public class OutboxWorker {
    private final OutboxProcessorService processorService;
    private final int BATCH_SIZE = 100;
    private final int RETRY_LIMIT = 10;

    public void process() {
        processorService.processBatch(Instant.now(),BATCH_SIZE,RETRY_LIMIT);
    }
}
