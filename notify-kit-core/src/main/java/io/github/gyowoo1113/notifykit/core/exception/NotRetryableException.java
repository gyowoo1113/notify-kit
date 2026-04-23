package io.github.gyowoo1113.notifykit.core.exception;

public class NotRetryableException extends NotifyKitException {

    public NotRetryableException(String code, String detail) {
        super(code,detail);
    }

    public NotRetryableException(String code, Throwable cause){
        super(code, cause);
    }
}
