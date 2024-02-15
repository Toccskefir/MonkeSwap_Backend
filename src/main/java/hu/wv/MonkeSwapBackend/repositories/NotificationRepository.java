package hu.wv.MonkeSwapBackend.repositories;

import hu.wv.MonkeSwapBackend.models.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

}
