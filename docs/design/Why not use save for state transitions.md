## Outbox Repository Design (Concurrency Consideration)

`OutboxRepository`는 일반적인 도메인 저장소(`NotificationRepository`)와 달리,  
단순한 `save()` 중심의 CRUD 방식이 아닌 **조건부 쿼리 기반의 상태 전이 메서드**를 제공한다.

이는 Outbox가 **비즈니스 엔티티 저장소가 아니라 “전송 작업 큐(work queue)”의 역할**을 하기 때문이다.

### Why not use `save()` for state transitions?

Outbox 처리 흐름은 다음과 같은 단계로 구성된다.

`1) 처리 대상 조회 (findBatchForProcessing) 2) 작업 선점 (markProcessing / claim) 3) 실제 전송 수행 4) 성공/실패 상태 반영 (markSent / markFailed)`

이 과정에서 단순히 엔티티를 조회한 뒤 상태를 변경하고 `save()`를 호출하는 방식은  
**멀티 워커 또는 스케줄 중첩 실행 환경에서 동시성 문제가 발생할 수 있다.**

예를 들어, 두 개의 워커가 동시에 동일한 Outbox 레코드를 조회한 경우,

- 둘 다 해당 작업을 처리 대상으로 인식할 수 있고
- 결과적으로 동일한 메시지가 **중복 전송**될 수 있다.
### Atomic state transition via query

이를 방지하기 위해 OutboxRepository는 다음과 같은 메서드를 제공한다.

- `markProcessing` : PENDING/FAILED 상태인 작업만 PROCESSING으로 전이 (선점)
- `markSent` : PROCESSING 상태인 작업만 SENT로 전이
- `markFailed` : PROCESSING 상태인 작업만 FAILED로 전이 및 재시도 정보 기록

이 메서드들은 모두 **조건부 UPDATE 쿼리(CAS: Compare-And-Set)** 로 구현되어,  
DB 레벨에서 상태 전이를 **원자적으로 보장**한다.

이를 통해:
- 하나의 작업은 반드시 하나의 워커만 처리하게 되고
- 스케줄 중첩 실행이나 멀티 인스턴스 환경에서도 안전한 처리가 가능하다.

### Contrast with NotificationRepository

`NotificationRepository`는 알림이라는 **비즈니스 도메인 엔티티**를 다루며,  
상태 변경(예: 읽음 처리)은 `NotificationService`에서 도메인 규칙에 따라 수행된다.

반면, `OutboxRepository`는

- 비즈니스 규칙이 아닌
- **작업 큐의 상태 관리 및 동시성 제어**가 핵심 책임이므로,

상태 전이 로직을 Repository 계층에 위치시키는 것이 설계적으로 적합하다.