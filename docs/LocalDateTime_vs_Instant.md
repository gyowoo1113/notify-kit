# LocalDateTime vs Instant

알림 시스템에서 시간 정보를 저장할 때
**글로벌 서비스 여부와 시간 기준의 일관성**에 따라 `Instant`와 `LocalDateTime`을 구분하여 사용함.

------

## Instant

- 날짜 + 시간 + UTC 기준
- 내부적으로 Unix Timestamp(`long`) 기반 저장
- TimeZone 해석 차이 발생하지 않음

### 사용 기준

- 글로벌 서비스 환경에서 사용
- 사용자 Region과 서버 Region이 서로 다른 경우 사용
- 여러 서버/인스턴스에서 동일한 시간 기준이 필요한 경우 사용

### 특징

- 서버 TimeZone 설정과 무관
- 저장 시 기준 시간은 항상 동일
- 클라이언트 표시 시 TimeZone 변환 필요

------

## LocalDateTime

- 날짜 + 시간 정보만 표현
- TimeZone 개념 없음
- 서버 기본 TimeZone 기준으로 해석

### 사용 기준

- 단일 Region 서비스에서 사용
- 서버 TimeZone이 고정된 환경에서 사용

### 주의 사항

- 서버마다 TimeZone이 다른 경우 구분 불가능
- 글로벌 서비스 환경에서는 시간 불일치 가능성 존재

------

## 선택 기준 요약

| 기준           | Instant        | LocalDateTime |
| -------------- | -------------- | ------------- |
| TimeZone 기준  | UTC 포함       | 없음          |
| 서버 환경 영향 | 없음           | 있음          |
| 글로벌 서비스  | 적합           | 부적합        |
| 단일 Region    | 사용 가능      | 사용 가능     |
| 저장 방식      | Unix Timestamp | 날짜/시간 값  |

------

## 결론

- 시간 기준의 일관성이 필요한 경우 `Instant` 사용
- 단일 Region 서비스에서는 `LocalDateTime` 사용 가능
- 공용 라이브러리 및 멀티 환경 고려 시 `Instant` 우선 사용