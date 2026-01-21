package io.github.gyowoo1113.notifykit.spring.api.request;

import io.github.gyowoo1113.notifykit.core.domain.notification.NotificationStatus;
import io.github.gyowoo1113.notifykit.core.exception.ValidationException;
import io.github.gyowoo1113.notifykit.core.support.PageRequestSpec;

public record NotificationListRequest(
        Long receiverId,
        NotificationStatus status,
        Integer page,
        Integer size
) {
    public Long receiverIdRequired() {
        if (receiverId == null) throw new ValidationException("notification", "receiverId", "required");
        return receiverId;
    }

    public PageRequestSpec toPageSpec(int defaultSize, int maxSize) {
        return PageRequestSpec.of(this.page, this.size, defaultSize, maxSize);
    }
}
