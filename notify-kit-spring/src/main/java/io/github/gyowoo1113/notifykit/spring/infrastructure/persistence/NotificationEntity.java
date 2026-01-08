package io.github.gyowoo1113.notifykit.spring.infrastructure.persistence;

import io.github.gyowoo1113.notifykit.core.domain.support.NotificationCommand;
import io.github.gyowoo1113.notifykit.core.domain.support.NotificationStatus;
import io.github.gyowoo1113.notifykit.core.domain.support.NotificationType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

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
    private LocalDateTime createdAt;

    @Column(name="readAt")
    private LocalDateTime readAt;

    // TODO : add from / ToModel (Notification을 쓸지 Command 쓸지 생각해봐야함)
}
