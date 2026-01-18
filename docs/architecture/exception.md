# Exception 설계 기준 (core ↔ spring)

본 프로젝트는 **core를 Spring으로부터 완전히 분리**하기 위해 예외를 역할에 따라 구분하여 설계함

------

## Exception 책임 분리

```
core           spring
--------------------------------
ResourceNotFound  → 404 Not Found
Conflict          → 409 Conflict
Validation        → 400 Bad Request
```

- core는 **HTTP 개념을 알지 못함**
- spring 계층에서 core 예외를 HTTP 응답으로 변환

------

## 설계 의도

- core를 Spring(Web) 환경으로부터 독립
- 비즈니스 규칙 위반을 **의미 있는 예외 타입**으로 표현
- Spring이 예외를 HTTP 응답으로 변환할 수 있도록 신호를 제공

> core는 “무슨 문제가 발생했는지”만 표현하고 spring은 “어떻게 응답할지”를 결정함

------

## Service Exception vs Domain Exception

| 구분        | Service        | Domain           |
| ----------- | -------------- | ---------------- |
| 관점        | 외부(API)      | 내부(객체 규칙)  |
| 목적        | 숨김/노출 정책 | 상태 불변성 유지 |
| 결과        | 404 Not Found  | 409 Conflict     |
| HTTP 인식   | 있음           | 없음             |
| 재사용 위치 | 조회, 목록     | 모든 행위        |

------

## Service Exception

- 외부 API 관점에서의 오류를 표현
- “존재하지 않음”, “접근 불가”와 같은 **노출 정책**을 담당
- 주로 조회/목록과 같은 읽기 작업에서 사용
- Spring 계층에서 HTTP 상태 코드로 매핑

예:

- `ResourceNotFoundException` → 404 Not Found

------

## Domain Exception

- 도메인 객체의 상태 불변성을 위반했음을 표현
- 외부 노출 여부와 무관한 **순수 비즈니스 규칙 오류**
- 도메인 행위 전반에서 재사용 가능
- HTTP 개념을 전혀 포함하지 않음

예:

- `ConflictException` → 상태 충돌 → 409 Conflict
- `ValidationException` → 규칙 위반 → 400 Bad Request

------

## 설계 효과

- core는 기술(Spring, HTTP)에 의존하지 않음
- 예외의 의미가 도메인/유스케이스 기준으로 명확해짐
- Spring Web 계층에서 일관된 예외 → 응답 매핑 가능
- 라이브러리로 사용 시 환경에 맞는 응답 전략 적용 가능