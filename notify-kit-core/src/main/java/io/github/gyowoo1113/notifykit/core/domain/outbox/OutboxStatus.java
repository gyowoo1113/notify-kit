package io.github.gyowoo1113.notifykit.core.domain.outbox;

public enum OutboxStatus {
    PENDING,
    SENT,
    FAILED,
    PROCESSING
}
