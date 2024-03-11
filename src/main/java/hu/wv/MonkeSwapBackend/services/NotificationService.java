package hu.wv.MonkeSwapBackend.services;

import hu.wv.MonkeSwapBackend.dtos.ItemDto;
import hu.wv.MonkeSwapBackend.dtos.NotificationDto;
import hu.wv.MonkeSwapBackend.exceptions.IsEmptyException;
import hu.wv.MonkeSwapBackend.model.Item;
import hu.wv.MonkeSwapBackend.model.Notification;
import hu.wv.MonkeSwapBackend.model.User;
import hu.wv.MonkeSwapBackend.repositories.NotificationRepository;
import hu.wv.MonkeSwapBackend.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository, UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    private List<NotificationDto> convertNotificationToNotificationDto(List<Notification> list) {
        List<NotificationDto> listToReturn = new ArrayList<>();
        list.forEach(item -> {
            NotificationDto dto = NotificationDto.builder()
                    .id(item.getId())
                    .message(item.getMessage())
                    .type(item.getType())
                    .build();
            listToReturn.add(dto);
        });
        return listToReturn;
    }

    public List<NotificationDto> getNotificationsByUserId(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            List<Notification> notifications = notificationRepository.findAllByUserId(user.get());
            return convertNotificationToNotificationDto(notifications);
        } else {
            throw new ObjectNotFoundException("userId", userId);
        }
    }

    @Transactional
    public void createNotification(Notification notification) {
        if (notification.getMessage().isBlank()) {
            throw new IsEmptyException("Message");
        }

        this.notificationRepository.save(notification);
    }
}
