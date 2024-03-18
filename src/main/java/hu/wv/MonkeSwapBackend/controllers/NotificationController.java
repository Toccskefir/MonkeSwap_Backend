package hu.wv.MonkeSwapBackend.controllers;

import hu.wv.MonkeSwapBackend.dtos.NotificationCreateDto;
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

    //GET endpoints
    @GetMapping
    public List<NotificationDto> getNotificationsByUserId() {
        return this.notificationService.getNotificationsByUserId();
    }

    //POST endpoints
    @PostMapping
    public void createNotification(@RequestBody NotificationCreateDto notification) {
        this.notificationService.createNotification(notification);
    }

    //DELETE endpoints
    @DeleteMapping()
    public void deleteAllNotification() {
        this.notificationService.deleteAllNotification();
    }
}
