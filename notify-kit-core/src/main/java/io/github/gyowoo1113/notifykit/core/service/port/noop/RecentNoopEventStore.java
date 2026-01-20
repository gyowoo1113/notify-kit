package io.github.gyowoo1113.notifykit.core.service.port.noop;

import io.github.gyowoo1113.notifykit.core.domain.SseEvent;
import io.github.gyowoo1113.notifykit.core.service.port.RecentEventStore;

import java.util.List;

public class RecentNoopEventStore implements RecentEventStore {
    @Override
    public void append(long receiverId, SseEvent event) {

    }

    @Override
    public List<SseEvent> findAfter(long receiverId, String lastEventId, int limit) {
        return null;
    }
}
