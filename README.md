## Project Overview

**notify-kit**은
알림 도메인의 **생성 · 저장 · 조회 · 읽음 처리 · 실시간 전송(SSE)** 흐름을
**라이브러리 형태로 재사용 가능**하도록 설계한 백엔드 중심 모듈이다.

Spring 환경에 종속되지 않은 core 모듈과 Spring Boot AutoConfiguration 기반의 starter 모듈을 분리하여,
여러 프로젝트에서 **최소한의 설정으로 알림 기능을 적용**하는 것을 목표로 한다.

------

## Key Goals

- 알림 도메인을 **라이브러리 형태로 재사용 가능**하게 제공
- 비즈니스 로직(core)과 기술 구현(Spring/JPA/Web)을 분리
- 저장 방식 및 전달 방식(SSE 등)을 **옵션으로 교체 가능**하게 설계
- 트랜잭션 정합성을 보장하는 이벤트 기반 구조 적용

------

## Core Features (Implemented)

- 알림 생성 / 단건 조회 / 목록 조회 / 읽음 처리
- Page 기반 페이징 + Cursor(No-Offset) 확장 구조
- Spring Event + `AFTER_COMMIT` 기반 알림 전송 트리거
- SSE(Server-Sent Events) 기반 실시간 알림 전송 (옵션)
- 전송 비활성화를 위한 Noop 구현체 제공
- Spring Boot AutoConfiguration 기반 손쉬운 적용
- core 모듈의 Spring/JPA 의존성 제거

------

## Design Highlights

- **Port & Adapter 구조**
    - core는 저장/전송 기술을 알지 않음
    - JPA, SSE는 별도 Adapter 모듈에서 구현
- **Facade 패턴**
    - 외부 프로젝트에서의 단일 진입점 제공
    - 트랜잭션 및 이벤트 오케스트레이션 담당
- **Event-driven + AFTER_COMMIT**
    - DB 커밋 이후에만 외부 전송 수행
    - 전송 실패가 비즈니스 로직에 영향 주지 않음

------

## Docs
설계 의도, 구조 설명, 트러블슈팅은 `docs/` 디렉토리에 정리되어 있음

### Architecture / Structure

- [전체 모듈 및 패키지 구조](/docs/architecture/layout.md)
- [NotificationEventPublisher & 이벤트 처리 구조](/docs/architecture/NotificationEventPublisher_&_이벤트_처리_구조.md)
- [ApplicationEventPublisher vs NotificationEventPublisher](/docs/architecture/ApplicationEventPublisher_vs_NotificationEventPublisher.md)
- [페이징 설계]

### Design Decisions (Why)

- [Why NotificationFacade](/docs/design/Why_NotificationFacade.md)
- [Why SSE is Optional](/docs/design/Why_SSE_is_Optional.md)
- [Why SSE instead of WebSocket](/docs/design/Why_SSE_instead_of_websocket.md)
- [LocalDateTime vs Instant](/docs/design/LocalDateTime_vs_Instant.md)
- [record + static factory 사용 이유](/docs/design/record-static-factory.md)

### Troubleshooting

- [RestController 의존성 주입 이슈](/docs/troubleshooting/RestController_의존성_주입_이슈(트러블슈팅).md)