package io.github.gyowoo1113.notifykit.spring.infrastructure.delivery.sse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SseEmitterRegistry {
    private final ConcurrentHashMap<Long, SseEmitter> emitters = new ConcurrentHashMap<>();

    public SseEmitter save(Long receiverId, SseEmitter emitter) {
        emitters.put(receiverId, emitter);
        return emitter;
    }

    public void remove(Long receiverId) {
        emitters.remove(receiverId);
    }

    public Optional<SseEmitter> get(Long receiverId) {
        return Optional.ofNullable(emitters.get(receiverId));
    }

    public boolean send(Long receiverId, String eventName, Object data) {
        SseEmitter emitter = emitters.get(receiverId);
        if (emitter == null) return false;

        try {
            emitter.send(SseEmitter.event().name(eventName).data(data));
            return true;
        } catch (Exception e) {
            remove(receiverId);
            return false;
        }
    }
}
