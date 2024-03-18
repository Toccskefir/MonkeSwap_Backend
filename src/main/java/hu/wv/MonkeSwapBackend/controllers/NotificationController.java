package hu.wv.MonkeSwapBackend.controllers;

import hu.wv.MonkeSwapBackend.dtos.NotificationCreateDto;
import hu.wv.MonkeSwapBackend.dtos.NotificationDto;
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
    //returns a lift of notifications of the logged-in user
    @GetMapping
    public List<NotificationDto> getNotificationsByUserId() {
        return this.notificationService.getNotificationsByUserId();
    }

    //POST endpoints
    //creates a notification with given userId
    @PostMapping
    public void createNotification(@RequestBody NotificationCreateDto notification) {
        this.notificationService.createNotification(notification);
    }

    //DELETE endpoints
    //deletes all notifications of the logged-in user
    @DeleteMapping()
    public void deleteAllNotification() {
        this.notificationService.deleteAllNotification();
    }
}
