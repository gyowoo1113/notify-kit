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

## Roadmap (Planned)

### 1) SSE 실사용 품질 개선

- 한 사용자 다중 연결 지원 (여러 탭/여러 기기 동시 접속)
- 연결 유지용 `ping` 이벤트 전송 (idle timeout 완화)
- 끊김 재연결 시 최근 이벤트 일부 재전송 지원
    - 서버가 이벤트에 `id` 포함
    - `Last-Event-ID`가 있으면 최근 N개 범위에서 재전송
- 서버 재시작으로 재전송 캐시가 사라질 수 있음
    - 누락 알림은 목록 조회 API로 복구하는 방식 유지

### 2) 전송 결과 기록 (Delivery Log)

- 알림 전송 성공/실패 기록
- 실패 원인(예외 메시지/코드) 기록
- 재시도 횟수/마지막 시도 시간 기록
- 운영 확인을 위한 최소 조회 API 또는 로그 출력 추가

### 3) 신뢰성 강화 (Outbox 기반)

- 알림 저장 트랜잭션과 전송을 분리
- 커밋 이후 전송을 보장하는 전송 큐(outbox) 저장
- 실패 시 재시도 정책 적용
- 중복 전송 방지(eventId 기준) 처리

### 4) 확장 옵션 (Later)

- Idempotency Key 기반 중복 생성 방지 (선택)
- 멀티테넌시 격리(tenantId/projectKey) 지원 (선택)
- 전송 채널 확장 (FCM / WebSocket 등) (선택)

------

## Docs
설계 의도, 구조 설명, 트러블슈팅은 `docs/` 디렉토리에 정리되어 있음

### Architecture / Structure

- [전체 모듈 및 패키지 구조](/docs/architecture/layout.md)
- [NotificationEventPublisher & 이벤트 처리 구조](/docs/architecture/NotificationEventPublisher_&_이벤트_처리_구조.md)
- [ApplicationEventPublisher vs NotificationEventPublisher](/docs/architecture/ApplicationEventPublisher_vs_NotificationEventPublisher.md)
- [페이징 설계](/docs/architecture/페이징%20설계.md)

### Design Decisions (Why)

- [Why NotificationFacade](/docs/design/Why_NotificationFacade.md)
- [Why SSE is Optional](/docs/design/Why_SSE_is_Optional.md)
- [Why SSE instead of WebSocket](/docs/design/Why_SSE_instead_of_websocket.md)
- [LocalDateTime vs Instant](/docs/design/LocalDateTime_vs_Instant.md)
- [record + static factory 사용 이유](/docs/design/record-static-factory.md)

### Troubleshooting

- [RestController 의존성 주입 이슈](/docs/troubleshooting/RestController_의존성_주입_이슈(트러블슈팅).md)