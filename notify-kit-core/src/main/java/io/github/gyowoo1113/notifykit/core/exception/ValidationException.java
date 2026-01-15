package io.github.gyowoo1113.notifykit.core.exception;

public class ValidationException extends NotifyKitException {
    public ValidationException(String detail){
        super("VALIDATION",detail);
    }

    public ValidationException(String resource, String field, String rule){
        super("VALIDATION", resource + "." + field + "." + rule);
    }
}
