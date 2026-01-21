package io.github.gyowoo1113.notifykit.spring.jpa.infrastructure.persistence.notification;

import io.github.gyowoo1113.notifykit.core.domain.notification.Notification;
import io.github.gyowoo1113.notifykit.core.domain.notification.NotificationStatus;
import io.github.gyowoo1113.notifykit.core.domain.notification.NotificationType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name="notifications")
public class NotificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="receiverId")
    private Long receiverId;

    @Column(name="title")
    private String title;

    @Column(name="content")
    private String content;

    @Column(name="notification_type")
    private NotificationType notificationType;

    @Column(name="notification_status")
    private NotificationStatus notificationStatus;

    @Column(name="linkUrl")
    private String linkUrl;

    @Column(name="createdAt")
    private Instant createdAt;

    @Column(name="readAt")
    private Instant readAt;

    @Column(name="updatedAt")
    private Instant updatedAt;

    @Column(name="deletedAt")
    private Instant deletedAt;

    public static NotificationEntity from(Notification notification){
        NotificationEntity entity = new NotificationEntity();
        entity.id = notification.getId();
        entity.receiverId = notification.getReceiverId();
        entity.title = notification.getTitle();
        entity.content = notification.getContent();
        entity.notificationType = notification.getNotificationType();
        entity.notificationStatus = notification.getNotificationStatus();
        entity.linkUrl = notification.getLinkUrl();
        entity.createdAt = notification.getCreatedAt();
        entity.readAt = notification.getReadAt();
        entity.updatedAt = notification.getUpdatedAt();
        entity.deletedAt = notification.getDeletedAt();
        return entity;
    }

    public Notification toModel(){
        return Notification.builder()
                .id(id)
                .receiverId(receiverId)
                .title(title)
                .content(content)
                .notificationType(notificationType)
                .notificationStatus(notificationStatus)
                .linkUrl(linkUrl)
                .createdAt(createdAt)
                .readAt(readAt)
                .updatedAt(updatedAt)
                .deletedAt(deletedAt)
                .build();
    }
}
