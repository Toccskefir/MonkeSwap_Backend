package hu.wv.MonkeSwapBackend.controllers;

import hu.wv.MonkeSwapBackend.model.Notification;
import hu.wv.MonkeSwapBackend.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/notification")
public class NotificationController {
    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping
    public void createNotification(@RequestBody Notification notification) {
        this.notificationService.createNotification(notification);
    }
}
