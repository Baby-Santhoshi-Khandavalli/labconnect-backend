package com.labconnect.DTORequest.notification;



import com.labconnect.models.notification.Notification;
import lombok.Data;

@Data
public class UpdateNotificationStatusRequest {
    private Notification.Status status;
}
