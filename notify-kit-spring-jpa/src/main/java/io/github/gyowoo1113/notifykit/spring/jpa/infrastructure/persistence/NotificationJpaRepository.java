package io.github.gyowoo1113.notifykit.spring.jpa.infrastructure.persistence;

import io.github.gyowoo1113.notifykit.core.domain.support.NotificationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationJpaRepository extends JpaRepository<NotificationEntity, Long> {
    Page<NotificationEntity> findByReceiverIdAndDeletedAtIsNull(Long receiverId, Pageable pageable);

    Page<NotificationEntity> findByReceiverIdAndNotificationStatusAndDeletedAtIsNull(
            Long receiverId,
            NotificationStatus notificationStatus,
            Pageable pageable
    );
}
