package io.github.gyowoo1113.notifykit.core.exception;

import lombok.Getter;

@Getter
public abstract class NotifyKitException extends RuntimeException {
    private final String code;

    protected NotifyKitException(String code) {
        super(code);
        this.code = code;
    }

    protected NotifyKitException(String code, String detail){
        super(detail == null || detail.isBlank() ? code : detail);
        this.code = code;
    }
}
