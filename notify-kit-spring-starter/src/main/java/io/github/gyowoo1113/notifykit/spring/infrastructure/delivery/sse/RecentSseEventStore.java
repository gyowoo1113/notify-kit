package io.github.gyowoo1113.notifykit.spring.infrastructure.delivery.sse;

import io.github.gyowoo1113.notifykit.core.domain.event.SseEvent;
import io.github.gyowoo1113.notifykit.core.service.port.RecentEventStore;
import lombok.RequiredArgsConstructor;

import java.util.*;
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
        purgeExpired(dq,now);
        trimToSize(dq);
    }

    @Override
    public List<SseEvent> findAfter(long receiverId, String lastEventId, int limit) {
        Deque<SseEvent> events = store.get(receiverId);

        if (events == null || events.isEmpty()) return Collections.emptyList();
        if (lastEventId == null || lastEventId.isBlank()) return Collections.emptyList();
        if (limit <= 0) return Collections.emptyList();


        OptionalLong lastOpt = parseLastEventId(lastEventId);
        if (lastOpt.isEmpty()) return Collections.emptyList();
        long last = lastOpt.getAsLong();

        limit = Math.max(limit, RECENT_NUM);

        List<SseEvent> list = new ArrayList<>();
        for (SseEvent event : events) {
            if (event.eventId() <= last) continue;
            list.add(event);
            if (list.size() == limit) break;
        }

        return list;
    }

    private void purgeExpired(Deque<SseEvent> dq, long now){
        while (isPurge(dq.peekFirst(), now)){
            dq.pollFirst();
        }
    }

    private void trimToSize(Deque<SseEvent> dq){
        while (dq.size() > RECENT_NUM) dq.pollFirst();
    }

    private boolean isPurge(SseEvent event, long now){
        if (event == null) return false;
        return now - event.createdAtEpochMs() > TTL_MS;
    }

    private OptionalLong parseLastEventId(String lastEventId){
        try {
            return OptionalLong.of(Long.parseLong(lastEventId));
        } catch (NumberFormatException e) {
            return OptionalLong.empty();
        }
    }
}
