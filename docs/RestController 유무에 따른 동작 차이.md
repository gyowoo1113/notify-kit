@RestController 유무에 따른 동작 차이 정리
1. @RestController를 붙인 경우
- 해당 클래스는 Spring Bean으로 등록된다.
- 애플리케이션 시작 시점에 생성자 주입이 즉시 수행된다.
- 이 시점에 NotificationFacade Bean이 존재하지 않으면
→ 의존성 주입 실패

결과적으로 ApplicationContext 초기화가 중단되며 애플리케이션 부팅이 실패한다.

2. @RestController를 제거한 경우
- 해당 클래스는 Spring이 관리하지 않는 일반 클래스가 된다.
- Spring은 해당 클래스에 대해 의존성 주입을 시도하지 않는다.
- 따라서 컨텍스트 초기화는 끝까지 진행된다.

이후 CommandLineRunner(beanCount) 등 후속 실행 로직이 정상 수행된다.

3. 동작 차이 요약
   구분	                결과
   @RestController 있음	컨트롤러도 Bean → 의존성 주입 실패 → 부팅 실패
   @RestController 없음	일반 클래스 → 주입 시도 없음 → 부팅 성공

4. 본 문제에서 현상이 강하게 드러난 이유
- NotificationFacade는 단순히 생성이 지연된 것이 아니라,
- AutoConfiguration의 @ConditionalOnBean 조건 평가 단계에서 스킵되어 아예 Bean으로 등록되지 않았다.
- 따라서 컨트롤러 생성 시점에서 영원히 주입될 수 없는 상태였다.

5. 참고 (임시 우회 방법)
아래 방법들은 테스트 또는 실험 목적으로만 사용 가능하다.
- ObjectProvider<NotificationFacade> 사용
- @Autowired(required = false) 사용

이는 근본 해결책이 아니며,
올바른 해결은 AutoConfiguration 순서 및 조건을 조정하여 NotificationFacade가 정상적으로 Bean으로 등록되도록 하는 것이다.

