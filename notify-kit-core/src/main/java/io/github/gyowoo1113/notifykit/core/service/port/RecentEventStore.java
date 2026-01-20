package io.github.gyowoo1113.notifykit.core.service.port;

import io.github.gyowoo1113.notifykit.core.domain.SseEvent;

import java.util.List;

public interface RecentEventStore {
    void append(long receiverId, SseEvent event);
    List<SseEvent> findAfter(long receiverId, String lastEventId, int limit);
}
