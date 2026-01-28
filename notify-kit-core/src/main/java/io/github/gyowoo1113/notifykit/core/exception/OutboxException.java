package io.github.gyowoo1113.notifykit.core.exception;

public class OutboxException extends NotifyKitException {
    public OutboxException(String detail) {
        super("OUTBOX", detail);
    }

    public static OutboxException serializeFailed(String eventType) {
        return new OutboxException("OUTBOX.SERIALIZE_FAILED." + eventType);
    }

    public static OutboxException unsupportedEventType(String eventType) {
        return new OutboxException("OUTBOX.UNSUPPORTED_EVENT_TYPE." + eventType);
    }

    public static OutboxException saveFailed(String eventType){
        return new OutboxException("OUTBOX.SAVE_FAILED." + eventType);
    }

    public static OutboxException sendFailed(String transport) {
        return new OutboxException("OUTBOX.SEND_FAILED." + transport);
    }
}
