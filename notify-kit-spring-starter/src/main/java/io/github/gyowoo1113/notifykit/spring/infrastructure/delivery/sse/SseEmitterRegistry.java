package io.github.gyowoo1113.notifykit.spring.infrastructure.delivery.sse;

import io.github.gyowoo1113.notifykit.core.exception.ConflictException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class SseEmitterRegistry {
    private static final int MAX_CONNECTIONS_PER_USER = 3;
    private final ConcurrentHashMap<Long, ConcurrentHashMap<String, SseEmitter>> emitters = new ConcurrentHashMap<>();

    public SseEmitter save(Long receiverId, String connectionId, SseEmitter emitter) {
        ConcurrentHashMap<String, SseEmitter> conn = emitters.computeIfAbsent(receiverId, k -> new ConcurrentHashMap<>());

        if (conn.size() >= MAX_CONNECTIONS_PER_USER) {
            throw new ConflictException("sse", "connection_limit");
        }

        conn.put(connectionId, emitter);
        return emitter;
    }

    public void remove(Long receiverId, String connectionId) {
        ConcurrentHashMap<String, SseEmitter> conn = emitters.get(receiverId);
        if (conn == null) return;

        conn.remove(connectionId);

        if (conn.isEmpty()) {
            emitters.remove(receiverId);
        }
    }

    public int connectionCount(Long receiverId) {
        Map<String, SseEmitter> conn = emitters.get(receiverId);
        return conn == null ? 0 : conn.size();
    }

    public boolean send(Long receiverId, String eventName, Object data) {
        Map<String, SseEmitter> conn = emitters.get(receiverId);
        if (conn == null || conn.isEmpty()) return false;

        boolean isSend = false;
        for (Map.Entry<String, SseEmitter> entry : conn.entrySet()) {
            String connectionId = entry.getKey();
            SseEmitter emitter = entry.getValue();
            try {
                emitter.send(SseEmitter.event().name(eventName).data(data));
                isSend = true;
            } catch (Exception e) {
                remove(receiverId, connectionId);
                log.debug("[SSE] send failed -> remove. receiverId={}, connectionId={}, event={}", receiverId, connectionId, eventName);
            }
        }

        return isSend;
    }
}
