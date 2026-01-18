# Module & Package Structure

본 프로젝트는 **Port & Adapter + Spring Boot Starter 구조**를 기반으로
도메인 / 저장 기술 / 전달 기술을 명확히 분리한다.

------

## Responsibility Overview

- **core**
  비즈니스 규칙과 유스케이스
  (Spring, JPA, SSE 등 기술 의존성 없음)

- **spring-jpa**
  영속성 기술 구현
  (JPA 기반 저장소 Adapter)

- **spring-starter**
  외부 프로젝트 사용을 위한 진입점
  (Facade, AutoConfiguration, Web/SSE 옵션 제공)

---

## Package / Module Responsibility

- **infrastructure**
  기술 구현 영역
  (어떻게 저장할지 / 어떻게 전송할지 / 어떤 기술을 사용할지)
- **persistence**
  저장 기술 구현
  (JPA Entity, Redis, Mongo 등 실제 저장소)
- **delivery**
  전달 방식 구현
  (SSE, WebSocket, FCM, Email 등 외부 전송)

---

## Module Structure

## `notify-kit-core`

> **순수 도메인 + 유스케이스 모듈**

```
notify-kit-core
└─ domain
   ├─ Notification                    // 알림 도메인
   └─ support
      ├─ NotificationCreate           // 생성 Command
      ├─ NotificationUpdate           // 수정 Command
      ├─ NotificationStatus
      └─ NotificationType

└─ service
   ├─ NotificationService             // UseCase
   └─ port
      └─ NotificationRepository       // 저장소 Port (interface)

└─ exception
   ├─ NotifyKitException
   ├─ ValidationException
   ├─ ResourceNotFoundException
   └─ ConflictException

└─ service.support
   ├─ PageResult
   ├─ PageRequestSpec
   └─ CursorPage
```

### 특징

- 기술(Spring/JPA/SSE)에 전혀 의존하지 않음
- 저장 방식은 `NotificationRepository` Port로만 의존
- 다른 기술 구현이 추가되어도 변경 없음

------

## `notify-kit-spring-jpa`

> **JPA 기반 영속성 Adapter 모듈**

```
notify-kit-spring-jpa
└─ infrastructure
   └─ persistence
      ├─ NotificationEntity
      ├─ NotificationJpaRepository
      │    extends JpaRepository<NotificationEntity, Long>
      └─ NotificationRepositoryAdapter
           implements NotificationRepository

└─ config
   └─ NotifyJpaAutoConfiguration
```

### 특징

- core의 `NotificationRepository`를 JPA로 구현
- 저장 기술(JPA)을 core로부터 완전히 분리
- 다른 저장소(Redis, Mongo 등) 구현 시 별도 모듈로 확장 가능

------

## `notify-kit-spring-starter`

> **외부 프로젝트에서 바로 사용하기 위한 Starter**
> (Facade를 통한 단일 진입점 + AutoConfiguration + Web/SSE 옵션 제공)

```
notify-kit-spring-starter
└─ application
   └─ NotificationFacade              // 외부 진입점

└─ config
   ├─ NotifyAutoConfiguration         // core/jpa 조립
   └─ NotifyWebAutoConfiguration      // web/sse 옵션 구성

└─ infrastructure
   └─ delivery
      ├─ advice
      │  └─ ExceptionControllerAdvice // 공통 예외 응답
      │
      ├─ error
      │  └─ ErrorResponse
      │
      ├─ event
      │  ├─ NotificationEventPublisher        // 전송 Port
      │  ├─ NoopNotificationEventPublisher    // 기본 구현
      │  ├─ NotificationCreatedEvent
      │  └─ NotificationCreatedEventListener  // AFTER_COMMIT 처리
      │
      └─ sse
         ├─ SseEmitterRegistry
         ├─ SseSubscribeController
         └─ SseNotificationEventPublisher
```

------

## Event & Delivery Flow

```
NotificationFacade.create()
 └─ ApplicationEventPublisher.publishEvent()
     └─ NotificationCreatedEvent
         ↓ (AFTER_COMMIT)
NotificationCreatedEventListener
 └─ NotificationEventPublisher.publishCreated()
     ├─ Noop (default)
     └─ SSE (notify.sse.enabled=true)
```

### 설계 포인트

- 알림 전송은 **트랜잭션 커밋 이후(AFTER_COMMIT)** 에만 수행
- SSE는 옵션 기능
    - 비활성화 시 `NoopNotificationEventPublisher` 사용
    - 활성화 시 `SseNotificationEventPublisher` 사용
- 비즈니스 로직은 전송 기술(SSE)에 의존하지 않음

------

## Dependency Direction

```
notify-kit-core
        ↑
notify-kit-spring-jpa
        ↑
notify-kit-spring-starter
```

- core는 어떤 기술 모듈에도 의존하지 않음
- 기술 교체/확장이 모듈 단위로 가능

------

## Summary

- **core**: 변하지 않는 도메인과 유스케이스
- **spring-jpa**: 저장 기술 구현
- **spring-starter**: 사용성 + 옵션(SSE) 제공
- 이벤트 기반 + AFTER_COMMIT 구조를 통해
    - 데이터 정합성 보장
    - 전송 방식 확장 용이
    - 라이브러리로서의 사용성 확보