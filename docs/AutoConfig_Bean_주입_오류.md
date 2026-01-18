- NotifyAutoConfigure : Service -> Facade 주입
- NotifyJpaAutoConfigure : NotificationRepositoryAdapter 주입
- NotificationRepositoryAdapter는 core의 Repository impl 구현체이고,
- Adapter 내에서 JPA 사용을 위한 JpaRepostiry를 주입받아서 사용함
- 때문에 순서상으로 NotifyJpaAutoConfigure에서 Adapter 주입 -> Service -> Facade가 되어야 함
- NotifyAutoConfiguration의 @ConditionalOnBean 조건 평가가 repo 빈 생성보다 먼저 일어나서,
  그 시점엔 repo가 없다고 판단되어 notificationService/notificationFacade Bean 정의 자체가 스킵된 상태
- 나중에 NotifyJpaAutoConfiguration에서 repo 빈이 생기더라도 이미 NotifyAutoConfiguration은
   “안 만들기로 결정”해버려서 service/facade가 끝까지 0개로 남게 됨

- => NotifyAutoConfiguration이 JPA 오토컨피그 이후에 처리되면,
  그때는 NotificationRepositoryAdapter Bean이 존재하고 있기 때문에
  @ConditionalOnBean(NotificationRepository.class) : true
  → Service 생성
  → Facade 생성
  이 순서가 자연스럽게 성립

- @ConditionalOnBean(NotificationRepository.class)는 Bean이 이미 존재하는지를 먼저 검사함
- 이 검사는 AutoConfig를 적용할지 말지 결정하는 단계에서 발생
- 그 시점에서는 JpaAutoConfiguration이 아직 repository bean을 만들기 이전일 수 있음
- 즉 스프링이 "순서 자동으로 맞추는 것" = 빈 생성 단계
- 오류난부분 = autoConfig / Conditional 평가단계




