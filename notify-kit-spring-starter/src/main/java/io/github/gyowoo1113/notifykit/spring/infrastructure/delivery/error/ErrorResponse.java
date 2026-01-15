package io.github.gyowoo1113.notifykit.spring.infrastructure.delivery.error;

public record ErrorResponse(String code, String message) {
    public static ErrorResponse of(String code, String message){
        return new ErrorResponse(code,message);
    }
}
