# NotificationEventPublisher & 이벤트 처리 구조

본 프로젝트의 알림 전송은 **비즈니스 로직과 전송 로직을 분리**하고,
**DB 트랜잭션 커밋 이후에만 알림을 전송**하도록 설계되어 있다.

이를 위해 다음 두 가지 개념을 함께 사용한다.

- **Spring Application Event**
- **NotificationEventPublisher (전송 전략 인터페이스)**

------

## 1. 핵심 설계 목표

- 알림 저장(DB)과 알림 전송(SSE)을 분리한다
- 알림 전송 실패가 비즈니스 트랜잭션을 망치지 않도록 한다
- DB 커밋 이전에 클라이언트로 알림이 나가는 것을 방지한다
- 전송 방식(SSE, Noop 등)을 옵션으로 교체 가능하게 만든다

------

## 2. 전체 흐름 요약

```
NotificationFacade.create()
 └─ Notification 저장 (트랜잭션)
 └─ Spring Event 발행 (NotificationCreatedEvent)
      ↓
[트랜잭션 커밋 성공]
      ↓
@TransactionalEventListener(AFTER_COMMIT)
 └─ NotificationEventPublisher.publishCreated()
      ↓
(SSE 활성화 시) SseNotificationEventPublisher
(SSE 비활성화 시) NoopNotificationEventPublisher
```

------

## 3. NotificationEventPublisher의 역할

```
public interface NotificationEventPublisher {
    void publishCreated(Notification notification);
}
```

`NotificationEventPublisher`는 **“알림을 어떻게 전송할지”에 대한 추상화**이다.

- 알림을 *언제* 보낼지는 이 인터페이스의 책임이 아님
- 알림을 *어떤 방식으로* 보낼지만 책임진다

### 구현체 예시

- `SseNotificationEventPublisher`
  → SSE를 통해 실시간 알림 전송
- `NoopNotificationEventPublisher`
  → 아무 동작도 하지 않음 (옵션 비활성화 시)

이를 통해 전송 방식은 **설정으로 교체 가능**하며,
비즈니스 로직은 전송 기술(SSE 등)에 의존하지 않는다.

------

## 4. ApplicationEventPublisher를 사용하는 이유

Facade에서 알림 전송을 직접 호출하지 않고,
Spring의 `ApplicationEventPublisher`를 통해 **도메인 이벤트를 발행**한다.

```
applicationEventPublisher.publishEvent(
    new NotificationCreatedEvent(saved)
);
```

이 방식의 핵심 목적은:

- 알림 전송을 **트랜잭션 커밋 이후**로 미루기 위함
- 외부 통신(SSE)이 트랜잭션에 영향을 주지 않게 하기 위함

------

## 5. AFTER_COMMIT 이벤트 리스너

```
@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
public void onCreated(NotificationCreatedEvent event) {
    eventPublisher.publishCreated(event.notification());
}
```

이 리스너는 다음 조건을 만족할 때만 실행된다.

- 현재 트랜잭션이 **정상적으로 커밋된 경우**
- 롤백된 경우에는 **절대 실행되지 않음**

이를 통해 다음 문제가 예방된다.

- DB에는 없는 알림이 클라이언트에 먼저 전달되는 문제
- SSE 전송 실패로 인해 알림 저장이 롤백되는 문제

------

## 6. 왜 NotificationCreatedEvent가 필요한가

```
public record NotificationCreatedEvent(Notification notification) {}
```

이 이벤트 클래스는 단순한 데이터 홀더지만, 중요한 역할을 한다.

- “알림이 생성되었다”는 **의미 있는 이벤트 타입**을 명확히 표현
- 생성/수정/삭제 이벤트 확장 시 구조 유지 가능
- Outbox, MQ(Kafka 등)로 확장할 때 그대로 재사용 가능

`Notification` 자체를 이벤트로 사용하지 않고
별도의 이벤트 타입을 두는 이유는 **의미 명확성과 확장성** 때문이다.

------

## 7. 즉시 호출 방식과의 비교

### 즉시 호출 방식 (사용하지 않음)

```
eventPublisher.publishCreated(notification);
```

- 트랜잭션 내부에서 실행될 수 있음
- 커밋 이전에 알림이 전송될 수 있음
- 전송 실패 시 롤백 위험 존재

### 현재 구조

```
applicationEventPublisher.publishEvent(...)
```

- 커밋 이후에만 전송
- 전송 실패가 비즈니스 로직에 영향 없음
- 실무에서 선호되는 이벤트 처리 방식

------

## 8. 정리

- `NotificationEventPublisher`는 **전송 전략 인터페이스**
- `ApplicationEventPublisher`는 **이벤트 발행 메커니즘**
- `@TransactionalEventListener(AFTER_COMMIT)`는 **커밋 이후 처리 보장**
- 이 구조를 통해 알림 시스템은
    - 안정적이고
    - 확장 가능하며
    - 라이브러리 형태로도 사용 가능한 구조를 갖는다