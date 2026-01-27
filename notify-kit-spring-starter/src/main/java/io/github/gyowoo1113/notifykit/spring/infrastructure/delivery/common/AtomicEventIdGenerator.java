package io.github.gyowoo1113.notifykit.spring.infrastructure.delivery.common;

import io.github.gyowoo1113.notifykit.core.service.port.EventIdGenerator;

import java.util.concurrent.atomic.AtomicLong;

public class AtomicEventIdGenerator implements EventIdGenerator {
    private final AtomicLong seq = new AtomicLong(0);

    @Override
    public long nextId() {
        return seq.incrementAndGet();
    }
}
