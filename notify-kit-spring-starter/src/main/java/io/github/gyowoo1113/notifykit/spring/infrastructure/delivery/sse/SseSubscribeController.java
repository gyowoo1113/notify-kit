package io.github.gyowoo1113.notifykit.spring.infrastructure.delivery.sse;

import io.github.gyowoo1113.notifykit.core.domain.SseEvent;
import io.github.gyowoo1113.notifykit.core.service.port.RecentEventStore;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/notification/sse")
@RequiredArgsConstructor
public class SseSubscribeController {
    private final SseEmitterRegistry registry;
    private final RecentEventStore recentEventStore;
    private static final long DEFAULT_TIMEOUT = 60 * 60 * 1000L; // 1시간
    private static final int REPLAY_LIMIT = 50; // RecentEventStore.RECENT_NUM

    @GetMapping(value = "/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe(@RequestParam Long receiverId,
                                @RequestHeader(value = "Last-Event-ID", required = false) String lastEventId) throws IOException {
        String connectionId = UUID.randomUUID().toString();

        SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);

        registry.save(receiverId, connectionId, emitter);

        emitter.onCompletion(() -> registry.remove(receiverId, connectionId));
        emitter.onTimeout(() -> registry.remove(receiverId, connectionId));
        emitter.onError(e -> registry.remove(receiverId, connectionId));

        // connection test
        try {
            emitter.send(SseEmitter.event().name("connected").data(
                    Map.of("ok", true,
                            "receiverId", receiverId,
                            "connectionId", connectionId)
            ));
        } catch (Exception ignored) {
        }

        // replay send
        if (lastEventId == null || lastEventId.isBlank()) return emitter;

        List<SseEvent> missed = recentEventStore.findAfter(receiverId, lastEventId, REPLAY_LIMIT);
        for (SseEvent event : missed) {
            emitter.send(
                    SseEmitter.event()
                            .id(String.valueOf(event.eventId()))
                            .name("notification")
                            .data(event.payload())
            );
        }

        return emitter;
    }
}
