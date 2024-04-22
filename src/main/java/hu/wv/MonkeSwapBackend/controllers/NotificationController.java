package hu.wv.MonkeSwapBackend.controllers;

import hu.wv.MonkeSwapBackend.dtos.NotificationCreateDto;
import hu.wv.MonkeSwapBackend.dtos.NotificationDto;
import hu.wv.MonkeSwapBackend.services.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Returns a list of notifications of the logged-in user")
    @GetMapping
    public List<NotificationDto> getNotificationsByUserId() {
        return this.notificationService.getNotificationsByUserId();
    }

    //POST endpoints
    @Operation(summary = "Creates a notification with given userId")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Notification successfully sent", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid data supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @PostMapping
    public void createNotification(@RequestBody NotificationCreateDto notification) {
        this.notificationService.createNotification(notification);
    }

    //DELETE endpoints
    @Operation(summary = "Deletes all notifications of the logged-in user")
    @DeleteMapping()
    public void deleteAllNotification() {
        this.notificationService.deleteAllNotification();
    }
}
