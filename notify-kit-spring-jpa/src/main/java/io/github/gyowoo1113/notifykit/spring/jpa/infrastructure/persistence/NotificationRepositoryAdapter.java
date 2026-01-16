package io.github.gyowoo1113.notifykit.spring.jpa.infrastructure.persistence;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.github.gyowoo1113.notifykit.core.domain.Notification;
import io.github.gyowoo1113.notifykit.core.domain.support.NotificationStatus;
import io.github.gyowoo1113.notifykit.core.service.port.NotificationRepository;
import io.github.gyowoo1113.notifykit.core.support.CursorPage;
import io.github.gyowoo1113.notifykit.core.support.PageResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class NotificationRepositoryAdapter implements NotificationRepository {

    private final NotificationJpaRepository repository;
    private final JPAQueryFactory jpaQueryFactory;

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
                ? repository.findByReceiverIdAndDeletedAtIsNull(receiverId, pageable)
                : repository.findByReceiverIdAndNotificationStatusAndDeletedAtIsNull(receiverId, status, pageable);

        return new PageResult<>(
                result.getContent().stream().map(NotificationEntity::toModel).toList(),
                result.getNumber(),
                result.getSize(),
                result.getTotalElements(),
                result.getTotalPages(),
                result.hasNext()
        );
    }

    @Override
    public CursorPage<Notification> listCursor(long receiverId, NotificationStatus status, Long cursor, int size) {
        QNotificationEntity entity = QNotificationEntity.notificationEntity;
        List<NotificationEntity> rows = jpaQueryFactory
                .selectFrom(entity)
                .where(
                        entity.receiverId.eq(receiverId),
                        status != null ? entity.notificationStatus.eq(status) : null,
                        cursor != null ? entity.id.lt(cursor) : null,
                        entity.deletedAt.isNull()
                )
                .orderBy(entity.id.desc())
                .limit(size + 1)
                .fetch();

        boolean hasNext = rows.size() > size;
        if (hasNext) rows = rows.subList(0,size);

        Long nextCursor = rows.isEmpty() ? null : rows.get(rows.size() - 1).getId();

        List<Notification> items = rows.stream()
                .map(NotificationEntity::toModel)
                .toList();

        return new CursorPage<>(items,nextCursor,hasNext);
    }

}
