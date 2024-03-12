package hu.wv.MonkeSwapBackend.repositories;

import hu.wv.MonkeSwapBackend.model.Notification;
import hu.wv.MonkeSwapBackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findAllByUserId(User user);
    void deleteAllByUserId(User user);
}
