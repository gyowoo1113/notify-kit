package io.github.gyowoo1113.notifykit.core.exception;

public class ResourceNotFoundException extends NotifyKitException {
    public ResourceNotFoundException(String resource, String id) {
        super("NOT_FOUND", resource + ":" + id);
    }

    public ResourceNotFoundException(String resource){
        super("NOT_FOUND", resource);
    }
}
