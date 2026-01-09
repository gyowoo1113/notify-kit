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
        NotificationEntity entity = NotificationEntity.from(notification);
        NotificationEntity saved = repository.save(entity);
        return saved.toModel();
    }

    @Override
    public Optional<Notification> getById(long id) {
        return repository.findById(id)
                .map(NotificationEntity::toModel);
    }
}
