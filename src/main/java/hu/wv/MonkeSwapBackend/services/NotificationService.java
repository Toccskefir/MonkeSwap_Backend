package hu.wv.MonkeSwapBackend.services;

import hu.wv.MonkeSwapBackend.dtos.NotificationDto;
import hu.wv.MonkeSwapBackend.exceptions.IsEmptyException;
import hu.wv.MonkeSwapBackend.model.Notification;
import hu.wv.MonkeSwapBackend.model.User;
import hu.wv.MonkeSwapBackend.repositories.NotificationRepository;
import hu.wv.MonkeSwapBackend.repositories.UserRepository;
import hu.wv.MonkeSwapBackend.utils.CommonUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository, UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
    }

    //READ methods
    public List<NotificationDto> getNotificationsByUserId() {
        User user = CommonUtil.getUserFromContextHolder();
        List<Notification> notifications = this.notificationRepository.findAllByUserId(user);
        return CommonUtil.convertNotificationToNotificationDto(notifications);
    }

    //CREATE methods
    @Transactional
    public void createNotification(Notification notification) {
        if (notification.getMessage().isBlank()) {
            throw new IsEmptyException("Message");
        }

        this.notificationRepository.save(notification);
    }

    //DELETE methods
    @Transactional
    public void deleteAllNotification() {
        User user = CommonUtil.getUserFromContextHolder();
        this.notificationRepository.deleteAllByUserId(user);
    }
}
