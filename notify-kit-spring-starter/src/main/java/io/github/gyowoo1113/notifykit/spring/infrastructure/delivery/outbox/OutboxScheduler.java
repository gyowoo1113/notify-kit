package io.github.gyowoo1113.notifykit.spring.infrastructure.delivery.outbox;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;

@RequiredArgsConstructor
public class OutboxScheduler {
    private final OutboxWorker worker;

    @Scheduled(fixedDelayString = "${notify.outbox.scheduler.fixed-delay:5000}")
    public void tick() {
        worker.process();
    }
}
