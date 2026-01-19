package io.github.gyowoo1113.notifykit.spring.infrastructure.delivery.event;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@RequiredArgsConstructor
public class NotificationCreatedEventListener {
    private final NotificationEventPublisher eventPublisher;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onCreated(NotificationCreatedEvent event) {
        try {
            eventPublisher.publishCreated(event.notification());
        } catch (Exception ignored) {

        }
    }
}
