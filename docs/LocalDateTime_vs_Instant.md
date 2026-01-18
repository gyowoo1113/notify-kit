Instant
- 날짜 + 시간 + UTC
- long 형태로 Unix Timestamp를 저장
- 글로벌 서비스일때 사용 (사용자의 Region, 속한 그룹의 Region이 다를 경우)

LocalDateTime
- 날짜 + 시간 (TimeZone이 없음)
- 서버마다 Timezone이 각각 다를 경우 구분 불가능
- 글로벌 서비스가 아닌 단일 region 서비스일때 사용