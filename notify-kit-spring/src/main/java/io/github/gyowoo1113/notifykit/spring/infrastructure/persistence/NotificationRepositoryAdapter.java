package io.github.gyowoo1113.notifykit.spring.infrastructure.persistence;

import io.github.gyowoo1113.notifykit.core.domain.Notification;
import io.github.gyowoo1113.notifykit.core.service.port.NotificationRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class NotificationRepositoryAdapter implements NotificationRepository {

    private final NotificationJPARepository repository;

    @Override
    public Notification save(Notification notification) {
        return null;
    }

    @Override
    public Optional<Notification> getById(long id) {
        return Optional.empty();
    }
}
