package io.github.gyowoo1113.notifykit.spring.infrastructure.delivery.sse;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/notification")
@RequiredArgsConstructor
public class SseSubscribeController {
    private final SseEmitterRegistry registry;
    private static final long DEFAULT_TIMEOUT = 60 * 60 * 1000L; // 1시간

    @GetMapping(value = "/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe(@RequestParam Long receiverId){
        SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);

        registry.save(receiverId,emitter);

        emitter.onCompletion(() -> registry.remove(receiverId));
        emitter.onTimeout(() -> registry.remove(receiverId));
        emitter.onError(e -> registry.remove(receiverId));

        try {
            emitter.send(SseEmitter.event().name("connected").data("ok"));
        } catch (Exception e){
            registry.remove(receiverId);
        }

        return emitter;
    }
}
