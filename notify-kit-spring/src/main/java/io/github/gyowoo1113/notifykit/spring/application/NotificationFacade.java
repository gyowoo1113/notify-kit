package io.github.gyowoo1113.notifykit.spring.application;

import io.github.gyowoo1113.notifykit.core.domain.Notification;
import io.github.gyowoo1113.notifykit.core.domain.support.NotificationCommand;
import io.github.gyowoo1113.notifykit.core.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NotificationFacade {
    private final NotificationService coreService;

    @Transactional
    public Notification create(NotificationCommand command){
        return coreService.create(command);
    }

    @Transactional(readOnly = true)
    public Notification getById(Long id){
        return coreService.getById(id);
    }
}
