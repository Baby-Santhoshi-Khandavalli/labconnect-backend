package com.labconnect.controller.notification;

import com.labconnect.DTORequest.notification.CreateNotificationRequest;
import com.labconnect.DTORequest.notification.UpdateNotificationStatusRequest;
import com.labconnect.DTOResponse.notification.NotificationResponse;
import com.labconnect.services.notification.NotificationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService service;

    public NotificationController(NotificationService service) {
        this.service = service;
    }

    @PostMapping
    public NotificationResponse createNotification(@RequestBody CreateNotificationRequest request) {
        return service.createNotification(request);
    }

    @GetMapping("/user/{userId}")
    public List<NotificationResponse> getByUser(@PathVariable Long userId) {
        return service.getNotificationsByUser(userId);
    }

    @PutMapping("/{id}")
    public NotificationResponse updateStatus(@PathVariable Long id,
                                             @RequestBody UpdateNotificationStatusRequest request) {
        return service.updateStatus(id, request);
    }
}

