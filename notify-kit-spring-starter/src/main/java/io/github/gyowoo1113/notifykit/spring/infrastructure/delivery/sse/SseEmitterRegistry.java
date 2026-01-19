package io.github.gyowoo1113.notifykit.spring.infrastructure.delivery.sse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class SseEmitterRegistry {
    private final ConcurrentHashMap<Long, ConcurrentHashMap<String, SseEmitter>> emitters = new ConcurrentHashMap<>();

    public SseEmitter save(Long receiverId, String connectionId, SseEmitter emitter) {
        emitters.computeIfAbsent(receiverId, k -> new ConcurrentHashMap<>())
                .put(connectionId, emitter);
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
