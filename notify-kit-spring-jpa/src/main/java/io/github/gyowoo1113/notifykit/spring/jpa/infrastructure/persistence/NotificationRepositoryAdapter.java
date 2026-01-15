package io.github.gyowoo1113.notifykit.spring.jpa.infrastructure.persistence;

import io.github.gyowoo1113.notifykit.core.domain.Notification;
import io.github.gyowoo1113.notifykit.core.domain.support.NotificationStatus;
import io.github.gyowoo1113.notifykit.core.service.port.NotificationRepository;
import io.github.gyowoo1113.notifykit.core.support.PageResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class NotificationRepositoryAdapter implements NotificationRepository {

    private final NotificationJpaRepository repository;

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

    @Override
    public PageResult<Notification> list(long receiverId, NotificationStatus status, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt", "id"));

        Page<NotificationEntity> result = (status == null)
                ? repository.findByReceiverIdAndDeletedAtIsNull(receiverId,pageable)
                : repository.findByReceiverIdAndNotificationStatusAndDeletedAtIsNull(receiverId,status,pageable);

        return new PageResult<>(
                result.getContent().stream().map(NotificationEntity::toModel).toList(),
                result.getNumber(),
                result.getSize(),
                result.getTotalElements(),
                result.getTotalPages(),
                result.hasNext()
        );
    }

}
