

## ApplicationEventPublisher vs NotificationEventPublisher

본 프로젝트에는 이름이 비슷한 두 가지 “Publisher”가 등장하지만,
**역할과 책임은 완전히 다르다.**

------

### 1. ApplicationEventPublisher (Spring 제공)

```
applicationEventPublisher.publishEvent(
    new NotificationCreatedEvent(notification)
);
```

- Spring이 제공하는 **애플리케이션 내부 이벤트 메커니즘**

- 의미:

  > “애플리케이션 내부에서 Notification이 생성되었다”

- 역할:

    - 비즈니스 이벤트를 **컨테이너에 알리는 신호**
    - 실제 처리 로직을 수행하지 않음

- 책임 범위:

    - *언제* 이벤트가 발생했는지만 전달
    - *어떻게* 처리할지는 관여하지 않음

즉, `ApplicationEventPublisher`는
**알림 전송을 담당하는 컴포넌트가 아니라, 이벤트 전달용 메커니즘**이다.

------

### 2. NotificationEventPublisher (전송 전략 인터페이스)

```
public interface NotificationEventPublisher {
    void publishCreated(Notification notification);
}
```

- 알림을 **외부로 어떻게 전송할지**를 정의하는 포트(Port)
- 역할:
    - SSE, Noop 등 **전송 방식의 추상화**
- 구현체 예:
    - `SseNotificationEventPublisher`
    - `NoopNotificationEventPublisher`

`NotificationEventPublisher`는
**알림 전송 자체에 대한 책임**을 가진다.

------

### 3. 두 Publisher가 함께 사용되는 이유

이 구조는 다음과 같은 역할 분리를 의도한다.

```
Application Layer
 └─ ApplicationEventPublisher
     → “무슨 일이 발생했는지”만 알림

Infrastructure / Delivery Layer
 └─ NotificationEventPublisher
     → “그 일을 어떻게 외부로 전송할지” 처리
```

이를 통해:

- 비즈니스 로직은 전송 기술(SSE 등)에 의존하지 않고
- 알림 전송은 트랜잭션 커밋 이후(AFTER_COMMIT)에만 수행되며
- 전송 방식은 설정으로 교체 가능하다

------

### 4. 요약

- `ApplicationEventPublisher`
    - 애플리케이션 내부 이벤트 전달 메커니즘
    - “Notification이 생성되었다”는 사실만 전달
- `NotificationEventPublisher`
    - 알림 전송 전략 인터페이스
    - SSE/Noop 등 실제 전송 담당

두 컴포넌트는 이름만 비슷할 뿐,
**역할과 책임은 명확히 분리된 서로 다른 개념**이다.

