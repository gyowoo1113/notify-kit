- infrastructure = 기술구현 (어떻게 저장/전송/어떤 기술을 쓸지)
- persistence : 어디에 저장할지 (JPA Entity, Redis, Mongo)
- delivery : 어떻게 전달할지 (SSE, WebSocket, FCM, Email)


notifykit-core
├─ domain
│   └─ Notification              (순수 도메인)
├─ domain.support
│   └─ NotificationCommand       (입력 모델)
├─ service
│   └─ NotificationService       (UseCase)
└─ service.port
└─ NotificationRepository    (Port, interface)

notifykit-spring
├─ persistence
│   ├─ entity
│   │   └─ NotificationEntity    (@Entity)
│   ├─ repository
│   │   └─ NotificationJpaRepository
│   │        extends JpaRepository<NotificationEntity, Long>
│   └─ adapter
│       └─ JpaNotificationRepositoryAdapter
│            implements NotificationRepository
└─ autoconfigure
└─ NotifyKitAutoConfiguration