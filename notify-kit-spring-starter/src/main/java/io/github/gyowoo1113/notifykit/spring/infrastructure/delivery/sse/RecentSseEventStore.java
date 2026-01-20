package io.github.gyowoo1113.notifykit.spring.infrastructure.delivery.sse;

import io.github.gyowoo1113.notifykit.core.domain.SseEvent;
import io.github.gyowoo1113.notifykit.core.service.port.RecentEventStore;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

@RequiredArgsConstructor
public class RecentSseEventStore implements RecentEventStore {
    private final ConcurrentHashMap<Long, Deque<SseEvent>> store = new ConcurrentHashMap<>();
    private static final int RECENT_NUM = 50;
    private static final long TTL_MS = 60000L;

    @Override
    public void append(long receiverId, SseEvent event) {
        long now = System.currentTimeMillis();
        Deque<SseEvent> dq = store.computeIfAbsent(receiverId, k -> new ConcurrentLinkedDeque<>());

        dq.addLast(event);

        while (true){
            SseEvent oldSseEvent = dq.peekFirst();
            if (oldSseEvent == null) break;
            if (now - oldSseEvent.createdAtEpochMs()  <= TTL_MS) break;
            dq.pollFirst();
        }

        while (dq.size() > RECENT_NUM) dq.pollFirst();
    }

    @Override
    public List<SseEvent> findAfter(long receiverId, String lastEventId, int limit) {
        return null;
    }
    

}
