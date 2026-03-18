
package com.labconnect.DTOResponse.notification;

import com.labconnect.models.notification.Notification;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
//@Builder
public class NotificationResponse {
    private Long notificationId;
    private Long userId;
    private String message;
    private Notification.Category category;
    private Notification.Status status;
    private LocalDateTime createdDate;
}