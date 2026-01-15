package io.github.gyowoo1113.notifykit.core.exception;

public class ConflictException extends NotifyKitException {
    public ConflictException(String detail){
        super("CONFLICT", detail);
    }

    public ConflictException(String resource, String rule){
        super("CONFLICT", resource + "." + rule);
    }
}
