package hu.wv.MonkeSwapBackend.repositories;

import hu.wv.MonkeSwapBackend.models.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

}
