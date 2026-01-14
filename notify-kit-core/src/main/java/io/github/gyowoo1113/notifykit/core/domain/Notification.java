package io.github.gyowoo1113.notifykit.core.domain;

import io.github.gyowoo1113.notifykit.core.domain.support.NotificationCreate;
import io.github.gyowoo1113.notifykit.core.domain.support.NotificationStatus;
import io.github.gyowoo1113.notifykit.core.domain.support.NotificationType;
import io.github.gyowoo1113.notifykit.core.domain.support.NotificationUpdate;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
public class Notification {
    private final Long id;
    private final Long receiverId;
    private final String title;
    private final String content;
    private final NotificationType notificationType;
    private final NotificationStatus notificationStatus;
    private final String linkUrl;
    private final Instant createdAt;
    private final Instant readAt;
    private final Instant updatedAt;
    private final Instant deletedAt;

    @Builder(toBuilder = true)
    public Notification(Long id, Long receiverId, String title, String content, NotificationType notificationType, NotificationStatus notificationStatus, String linkUrl, Instant createdAt, Instant readAt, Instant updatedAt, Instant deletedAt) {
        this.id = id;
        this.receiverId = receiverId;
        this.title = title;
        this.content = content;
        this.notificationType = notificationType;
        this.notificationStatus = notificationStatus;
        this.linkUrl = linkUrl;
        this.createdAt = createdAt;
        this.readAt = readAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    private static <T> T or(T newVal, T oldVal){
        return newVal != null ? newVal : oldVal;
    }

    private void assertNotDeleted(){
        if (isDeleted()){
            throw new IllegalStateException("삭제된 알림은 변경 불가");
        }
    }

    public  boolean isDeleted(){
        return this.notificationStatus == NotificationStatus.DELETED && deletedAt != null;
    }

    public static Notification create(NotificationCreate notificationCreate, Instant now){
        return Notification.builder()
                .receiverId(notificationCreate.receiverId())
                .title(notificationCreate.title())
                .content(notificationCreate.content())
                .notificationType(notificationCreate.notificationType())
                .notificationStatus(NotificationStatus.UNREAD)
                .linkUrl(notificationCreate.linkUrl())
                .createdAt(now)
                .build();
    }

    public Notification update(NotificationUpdate notificationUpdate, Instant now){
        assertNotDeleted();
        return this.toBuilder()
                .title(or(notificationUpdate.title(), this.title))
                .content(or(notificationUpdate.content(),this.content))
                .linkUrl(or(notificationUpdate.linkUrl(),this.linkUrl))
                .updatedAt(now)
                .build();
    }

    public Notification markAsRead(Instant now){
        assertNotDeleted();
        if (this.notificationStatus == NotificationStatus.READ && this.readAt != null) return this;

        return this.toBuilder()
                .notificationStatus(NotificationStatus.READ)
                .readAt(now)
                .updatedAt(now)
                .build();
    }

    public Notification markAsUnread(Instant now){
        assertNotDeleted();
        if (this.notificationStatus == NotificationStatus.UNREAD && this.readAt == null) return this;

        return this.toBuilder()
                .notificationStatus(NotificationStatus.UNREAD)
                .readAt(null)
                .updatedAt(now)
                .build();
    }

    public Notification markAsDeleted(Instant now){
        if (isDeleted()) return this;
        return this.toBuilder()
                .deletedAt(now)
                .notificationStatus(NotificationStatus.DELETED)
                .updatedAt(now)
                .build();
    }
}
