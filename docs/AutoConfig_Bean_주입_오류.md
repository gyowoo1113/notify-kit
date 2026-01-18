# AutoConfiguration & Conditional Bean 주입 오류

본 오류는 **Spring Boot AutoConfiguration 처리 순서와 `@ConditionalOnBean` 평가 시점 차이**로 인해 발생함

------

## 구성 요소 역할

- **NotifyAutoConfiguration**
  - `NotificationService` Bean 생성
  - `NotificationFacade` Bean 생성
- **NotifyJpaAutoConfiguration**
  - `NotificationRepositoryAdapter` Bean 생성
- **NotificationRepositoryAdapter**
  - core 모듈의 `NotificationRepository` 구현체
  - 내부에서 Spring Data JPA의 `JpaRepository`를 주입받아 사용

------

## 의도한 Bean 생성 순서

```
NotifyJpaAutoConfiguration
 └─ NotificationRepositoryAdapter 생성
      ↓
NotifyAutoConfiguration
 └─ NotificationService 생성
      ↓
NotificationFacade 생성
```

- Repository Adapter가 먼저 존재해야 Service와 Facade가 정상적으로 생성됨

------

## 실제 발생한 문제

### 1. Conditional 평가 시점 문제

- `NotifyAutoConfiguration`에
  `@ConditionalOnBean(NotificationRepository.class)` 사용
- Spring Boot는 **AutoConfiguration 적용 여부를 결정하는 단계에서**
  `@ConditionalOnBean` 조건을 평가함

이 시점에는:

- `NotifyJpaAutoConfiguration`이 아직 처리되지 않았을 수 있음
- 따라서 `NotificationRepository` Bean이 **존재하지 않는다고 판단됨**

------

### 2. 잘못된 결정의 고착화

- 조건 평가 결과:

  ```
  @ConditionalOnBean(NotificationRepository.class) == false
  ```

- 그 결과:

  - `NotificationService` Bean 정의 스킵
  - `NotificationFacade` Bean 정의 스킵

이 결정은:

- **이후에 Repository Bean이 생성되더라도 다시 평가되지 않음**
- 즉, 한 번 “안 만들기로 결정”되면 끝까지 생성되지 않음

------

## 오류 원인

- “스프링이 빈 생성 순서를 자동으로 맞춰준다”는 것은  **이미 등록되기로 결정된 Bean들에 대한 이야기**
- `@ConditionalOnBean`은
  - **빈 생성 단계**가 아니라 **AutoConfiguration 적용 여부 결정 단계**에서 평가됨

------

## 해결 방식

### AutoConfiguration 순서 명시

`NotifyAutoConfiguration`이 `NotifyJpaAutoConfiguration` **이후에 처리되도록 보장**

그 결과:

- `NotifyAutoConfiguration` 평가 시점에는

  - `NotificationRepositoryAdapter` Bean 이미 존재

- 조건 평가:

  ```
  @ConditionalOnBean(NotificationRepository.class) == true
  ```

- 정상적인 Bean 생성 흐름 성립

```
NotificationRepositoryAdapter
 → NotificationService
 → NotificationFacade
```

------

## 요약

- `@ConditionalOnBean`은 “나중에 생길 Bean”을 고려하지 않음
- AutoConfiguration 적용 여부는 **초기 평가 시점에서 고정됨**
- Repository Adapter를 생성하는 AutoConfiguration이
  Service/Facade AutoConfiguration보다 **먼저 실행되어야 함**
- 따라서 AutoConfiguration 간 **명시적인 순서 제어가 필요함**