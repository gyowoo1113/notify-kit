# record에서 static factory(`of`)를 사용하는 이유

결론부터 정리하면 다음과 같음.

- `ErrorResponse.of(code, message)`는 현재 시점에서는 `new ErrorResponse(code, message)`와 **기능적으로 동일**
- 그럼에도 static factory를 사용하는 이유는 **미래 변경 대비**와 **의도 표현**을 위함

------

## 1. 생성 책임을 한 곳으로 모으기

현재 `ErrorResponse`는 다음과 같은 구조임

```
public record ErrorResponse(
    String code,
    String message
) {}
```

하지만 향후 아래와 같이 확장될 수 있음

```
public record ErrorResponse(
    String code,
    String message,
    Instant timestamp,
    String traceId
) {}
```

### `new`를 직접 사용하는 경우

```
new ErrorResponse(code, message, Instant.now(), traceId);
```

- 생성자 시그니처 변경 시 **모든 호출부 수정 필요**

### static factory를 사용하는 경우

```
public static ErrorResponse of(String code, String message) {
    return new ErrorResponse(code, message, Instant.now(), TraceContext.get());
}
```

호출부는 그대로 유지됨

```
ErrorResponse.of(code, message);
```

------

## 2. 의미 있는 이름을 부여할 수 있음

### `new` 사용 시

```
new ErrorResponse("NOT_FOUND", "notification:1");
```

- 단순 객체 생성
- 의미 전달 불가

### static factory 사용 시

```
ErrorResponse.of("NOT_FOUND", "notification:1");
```

또는 추후 다음과 같이 확장 가능

```
ErrorResponse.notFound("notification", id);
ErrorResponse.validation("notification.title.required");
```

- 메서드 이름 자체로 **의도 표현 가능**
- 호출부 가독성 향상

------

## 3. 생성 로직을 숨길 수 있음

현재는 단순 생성이지만, 향후 다음과 같은 로직이 추가될 수 있음.

- message 포맷 통일
- code 정규화
- null 방어
- 국제화(i18n) 키 변환

예시:

```
public static ErrorResponse of(String code, String message) {
    if (message == null) {
        message = "internal_error";
    }
    return new ErrorResponse(code, message);
}
```

- 호출부는 내부 로직 변경을 전혀 알 필요 없음
- 생성 규칙을 한 곳에서 관리 가능

------

## 4. record와 static factory의 궁합

record의 특징:

- 불변 객체
- 모든 필드를 받는 canonical constructor만 제공
- 생성자 오버로드가 제한적

이로 인해:

- 생성 시점의 로직 분기가 어려움
- static factory로 생성 책임을 위임하는 방식이 적합함

------

## 요약

- 현재는 `new`와 `static factory`가 동일하게 동작
- static factory는
  - 생성 책임 집중
  - 의미 있는 이름 제공
  - 생성 로직 은닉
  - record의 제약 보완
- 따라서 record 기반 DTO/응답 객체에서는 static factory 패턴을 기본 선택으로 사용