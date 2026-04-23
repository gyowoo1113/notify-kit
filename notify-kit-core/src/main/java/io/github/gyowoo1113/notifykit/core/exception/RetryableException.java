package io.github.gyowoo1113.notifykit.core.exception;

public class RetryableException extends NotifyKitException {

    public RetryableException(String code, String detail){
        super(code, detail);
    }

    public RetryableException(String code, Throwable cause){
        super(code, cause);
    }
}
