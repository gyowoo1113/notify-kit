package io.github.gyowoo1113.notifykit.core.domain;

import io.github.gyowoo1113.notifykit.core.domain.support.NotificationCreate;
import io.github.gyowoo1113.notifykit.core.domain.support.NotificationStatus;
import io.github.gyowoo1113.notifykit.core.domain.support.NotificationType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDateTime;

class NotificationTest {

    @Test
    public void NotificationCommand_객체로_생성할_수_있다(){
        // given
        NotificationCreate create = new NotificationCreate(
            10L
                ,"테스트 알림[제목]"
                ,"안녕하세요. 내용입니다"
                , NotificationType.COMMENT
                , NotificationStatus.UNREAD
                , null
                , Instant.now()
                , Instant.now()
                , null
                , null
        );

        // when
        Notification notification = Notification.from(create);

        // then
        Assertions.assertThat(notification.getId()).isNull();
        Assertions.assertThat(notification.getTitle()).isEqualTo("테스트 알림[제목]");
        Assertions.assertThat(notification.getContent()).isEqualTo("안녕하세요. 내용입니다");
        Assertions.assertThat(notification.getNotificationType()).isEqualTo(NotificationType.COMMENT);
        Assertions.assertThat(notification.getNotificationStatus()).isEqualTo(NotificationStatus.UNREAD);
        Assertions.assertThat(notification.getLinkUrl()).isNull();

    }

}