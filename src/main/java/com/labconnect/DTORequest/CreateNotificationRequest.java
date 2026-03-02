package com.labconnect.DTORequest;


import com.labconnect.models.notification.Notification;
import lombok.Data;

@Data
public class CreateNotificationRequest {
    private Long userId;
    private String message;
    private Notification.Category category;
}

