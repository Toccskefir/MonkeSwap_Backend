package hu.wv.MonkeSwapBackend.controllers;

import hu.wv.MonkeSwapBackend.dtos.NotificationDto;
import hu.wv.MonkeSwapBackend.model.Notification;
import hu.wv.MonkeSwapBackend.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/notification")
public class NotificationController {
    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/{userId}")
    public List<NotificationDto> getNotificationsByUserId(@PathVariable("userId") Long userId) {
        return this.notificationService.getNotificationsByUserId(userId);
    }

    @PostMapping
    public void createNotification(@RequestBody Notification notification) {
        this.notificationService.createNotification(notification);
    }

    @DeleteMapping()
    public void deleteAllNotification() {
        this.notificationService.deleteAllNotification();
    }
}
