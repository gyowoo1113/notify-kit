package io.github.gyowoo1113.notifykit.spring.infrastructure.delivery.sse;

import java.util.concurrent.atomic.AtomicLong;

public class AtomicEventIdGenerator {
    private final AtomicLong seq = new AtomicLong(0);

    public long nextId() {
        return seq.incrementAndGet();
    }
}
