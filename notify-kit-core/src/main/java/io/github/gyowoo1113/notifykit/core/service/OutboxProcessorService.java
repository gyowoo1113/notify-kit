package io.github.gyowoo1113.notifykit.core.service;

import io.github.gyowoo1113.notifykit.core.domain.outbox.OutboxMessage;
import io.github.gyowoo1113.notifykit.core.exception.OutboxException;
import io.github.gyowoo1113.notifykit.core.service.port.OutboxRepository;
import io.github.gyowoo1113.notifykit.core.service.port.OutboxSender;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.CompletionException;

@RequiredArgsConstructor
public class OutboxProcessorService {
    private final OutboxRepository repository;
    private final OutboxSender outboxSender;

    public void processBatch(Instant now, int batchSize, int retryLimit){
        List<OutboxMessage> messages = loadBatch(now,batchSize);

        for (OutboxMessage message : messages) {
            boolean isProcessed = claim(message.getId());
            if (!isProcessed) {
                continue;
            }

            try {
                dispatch(message);
                onSuccess(message.getId());
            } catch (Exception e) {
                onFailure(message,retryLimit,now);
            }
        }
    }

    private List<OutboxMessage> loadBatch(Instant now, int batchSize){
        return repository.findBatchForProcessing(now, batchSize);
    }

    private boolean claim(long id){
        return repository.markProcessing(id);
    }

    private void dispatch(OutboxMessage message){
        try {
            outboxSender.send(message).join();
        } catch (CompletionException e) {
        }
    }

    private void onSuccess(long id){
        repository.markSent(id);
    }

    private void onFailure(OutboxMessage message, int retryLimit, Instant now){
        int nextRetryCount = message.getRetryCount() + 1;
        if (nextRetryCount > retryLimit) {
            repository.markFailedFinal(message.getId());
            return;
        }

        repository.markFailed(message.getId(), now.plusSeconds(5));
    }
}
