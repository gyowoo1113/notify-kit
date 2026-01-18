core           spring
--------------------------------
ResourceNotFound  → 404 Not Found
Conflict          → 409 Conflict
Validation        → 400 Bad Request

### 의도
- core를 Srping에서 독립 
- Spring이 이해할 수 있게 신호를 주기위해 커스텀 Exeption 생성

구분	        Service         Domain
------------------------------------------------------------
관점	        외부(API)        내부(객체 규칙)
목적	        숨김/노출 정책    상태 불변성
결과	        404             409
HTTP 인식	있음	            없음
재사용 위치	조회, 목록	    모든 행위