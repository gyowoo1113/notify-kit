결론
- ErrorResponse.of(code, message)는 지금 당장은 new ErrorResponse(code, message)랑 기능적으로 완전히 같음
- 그럼에도 쓰는 이유는 미래 대비 + 의도 표현을 위해

1️⃣ 생성 책임을 “한 곳”으로 모으기
- 지금은 필드가 2개지만, 나중에 이렇게 바뀔 수 있음:
`public record ErrorResponse(
String code,
String message,
Instant timestamp,
String traceId
) {}`

- ❌ new를 직접 쓰면
`new ErrorResponse(code, message, Instant.now(), traceId);`
-> 모든 호출부 수정 필요

- ✅ static factory 쓰면
`public static ErrorResponse of(String code, String message) {
return new ErrorResponse(code, message, Instant.now(), TraceContext.get());
}`
-> 호출부는 그대로
`ErrorResponse.of(code, message);`

2️⃣ “의미 있는 이름”을 붙일 수 있음
- new는 의미가 없음
`new ErrorResponse("NOT_FOUND", "notification:1");`
- 하지만 static factory는:
`ErrorResponse.of("NOT_FOUND", "notification:1");`
- 혹은 나중에:
`ErrorResponse.notFound("notification", id);
ErrorResponse.validation("notification.title.required");`
👉 의도를 메서드 이름으로 드러낼 수 있음

3️⃣ 생성 로직을 숨길 수 있음
- 지금은 단순하지만, 나중엔 이런 것도 가능:
    - message 포맷 통일
    - code 정규화
    - null 방어
    -국제화(i18n) 키 변환

`public static ErrorResponse of(String code, String message){
if (message == null) {
message = "internal_error";
}
return new ErrorResponse(code, message);
}`
-> 호출부는 아무 것도 모름

4️⃣ record + static factory는 궁합이 좋음
- record는:
    - 불변
    - 생성자 오버로드 제한적
