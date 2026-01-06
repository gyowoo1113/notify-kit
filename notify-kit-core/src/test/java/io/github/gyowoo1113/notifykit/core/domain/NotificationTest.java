package io.github.gyowoo1113.notifykit.core.domain;

import io.github.gyowoo1113.notifykit.core.domain.support.NotificationCommand;
import io.github.gyowoo1113.notifykit.core.domain.support.NotificationStatus;
import io.github.gyowoo1113.notifykit.core.domain.support.NotificationType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class NotificationTest {

    @Test
    public void NotificationCommand_객체로_생성할_수_있다(){
        // given
        NotificationCommand command = new NotificationCommand(
            10L
                ,"테스트 알림[제목]"
                ,"안녕하세요. 내용입니다"
                , NotificationType.COMMENT
                , NotificationStatus.UNREAD
                , null
                , LocalDateTime.now()
                , LocalDateTime.now()
        );

        // when
        Notification notification = Notification.from(command);

        // then
        Assertions.assertThat(notification.getId()).isNull();
        Assertions.assertThat(notification.getTitle()).isEqualTo("테스트 알림[제목]");
        Assertions.assertThat(notification.getContent()).isEqualTo("안녕하세요. 내용입니다");
        Assertions.assertThat(notification.getNotificationType()).isEqualTo(NotificationType.COMMENT);
        Assertions.assertThat(notification.getNotificationStatus()).isEqualTo(NotificationStatus.UNREAD);
        Assertions.assertThat(notification.getLinkUrl()).isNull();

    }

}