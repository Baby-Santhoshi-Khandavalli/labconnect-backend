package com.labconnect.DTORequest;

import com.labconnect.models.Notification;
import lombok.Data;

@Data
public class UpdateNotificationStatusRequest {
    private Notification.Status status;
}
