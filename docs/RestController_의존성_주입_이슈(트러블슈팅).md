# `@RestController` 유무에 따른 동작 차이

본 문서는 `@RestController` 존재 여부에 따라
**Spring Bean 등록 및 의존성 주입 시점이 어떻게 달라지는지**를 정리함.

본 이슈는 **`NotificationFacade` AutoConfiguration 설정 과정에서 발생한 문제**가
컨트롤러 Bean 생성 시점에 노출되며 드러난 사례임.

------

## 1. `@RestController`를 붙인 경우

- 해당 클래스는 Spring Bean으로 등록됨
- 애플리케이션 시작 시점에 Bean 생성 및 생성자 주입이 즉시 수행됨
- 이 시점에 `NotificationFacade` Bean이 존재하지 않으면
  → 의존성 주입 실패 발생

### 결과

- `ApplicationContext` 초기화 중단
- 애플리케이션 부팅 실패

------

## 2. `@RestController`를 제거한 경우

- 해당 클래스는 Spring이 관리하지 않는 일반 클래스가 됨
- Spring은 해당 클래스에 대해 의존성 주입을 시도하지 않음
- 컨텍스트 초기화가 끝까지 정상 진행됨

### 결과

- `ApplicationContext` 정상 초기화
- `CommandLineRunner` 등 후속 실행 로직 정상 수행

------

## 3. 동작 차이 요약

| 구분                   | 결과                                           |
| ---------------------- | ---------------------------------------------- |
| `@RestController` 있음 | 컨트롤러도 Bean → 의존성 주입 시도 → 부팅 실패 |
| `@RestController` 없음 | 일반 클래스 → 주입 시도 없음 → 부팅 성공       |

------

## 4. 문제 발생 원인

- 본 문제는 컨트롤러 자체의 문제가 아님
- `NotificationFacade`가 **AutoConfiguration 조건 평가 과정에서 Bean으로 등록되지 않은 상태**에서 발생
- `@RestController`는 애플리케이션 시작 시점에 Bean 생성을 강제하므로
  Facade Bean 부재 문제가 즉시 노출됨

즉,

- Facade는 “지연 생성된 Bean”이 아니라
- **AutoConfiguration 설정 단계에서 생성 대상에서 제외된 Bean**이었음
- 이 상태에서 컨트롤러가 Bean으로 등록되며 의존성 주입 실패가 발생함

------

## 결론

- `@RestController`는 Bean 등록과 생성자 주입을 즉시 수행함
- Facade AutoConfiguration 설정이 올바르지 않은 경우
  컨트롤러 Bean 생성 단계에서 문제가 바로 드러남
- 본 이슈의 핵심은
  **컨트롤러 유무가 아니라 Facade AutoConfiguration 설정 과정에 있음**